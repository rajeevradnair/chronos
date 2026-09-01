import json
import unittest
from unittest.mock import patch

from evidence_fusion.fixtures import (
    GLOBALHOTELS_DECISION_FACTS,
    GLOBALHOTELS_EVIDENCE,
)
from evidence_fusion.fusion import (
    build_evidence_bundle,
)
from reasoning.models import (
    ExplanationClaim,
    GroundedExplanation,
)
from reasoning.reasoner import (
    BedrockReasoner,
    validate_grounding,
)


QUESTION = "Why did we approve 17% for GlobalHotels?"


def build_test_bundle():
    return build_evidence_bundle(
        question=QUESTION,
        decision_facts=GLOBALHOTELS_DECISION_FACTS,
        evidence=GLOBALHOTELS_EVIDENCE,
    )


VALID_EXPLANATION = {
    "answer": (
        "The 17% discount was approved in the context "
        "of competitive pricing pressure."
    ),
    "claims": [
        {
            "statement": "The discount was 17%.",
            "claim_type": "fact",
            "decision_fact_names": [
                "discount_percent",
            ],
            "evidence_ids": [],
        },
        {
            "statement": (
                "The customer reported materially "
                "lower competitor pricing."
            ),
            "claim_type": "fact",
            "decision_fact_names": [],
            "evidence_ids": [
                "CALL-44",
            ],
        },
        {
            "statement": (
                "Competitive pricing pressure contributed "
                "to the discount exception."
            ),
            "claim_type": "inference",
            "decision_fact_names": [
                "discount_percent",
                "approval_status",
            ],
            "evidence_ids": [
                "CALL-44",
                "POLICY-V2",
            ],
        },
    ],
}


class FakeBedrockClient:

    def __init__(self, response):
        self.response = response
        self.last_request = None

    def converse(self, **kwargs):
        self.last_request = kwargs

        return {
            "output": {
                "message": {
                    "content": [
                        {
                            "text": json.dumps(
                                self.response
                            ),
                        }
                    ]
                }
            }
        }


class ReasoningTests(unittest.TestCase):

    def test_valid_grounded_explanation(self):
        bundle = build_test_bundle()

        explanation = (
            GroundedExplanation.model_validate(
                VALID_EXPLANATION
            )
        )

        validate_grounding(
            bundle=bundle,
            explanation=explanation,
        )

    def test_unknown_evidence_id_is_rejected(self):
        bundle = build_test_bundle()

        explanation = GroundedExplanation(
            answer="Test answer",
            claims=[
                ExplanationClaim(
                    statement="Unsupported claim.",
                    claim_type="fact",
                    decision_fact_names=[],
                    evidence_ids=["CALL-999"],
                )
            ],
        )

        with self.assertRaisesRegex(
            ValueError,
            "unknown evidence IDs",
        ):
            validate_grounding(
                bundle=bundle,
                explanation=explanation,
            )

    def test_unknown_decision_fact_is_rejected(self):
        bundle = build_test_bundle()

        explanation = GroundedExplanation(
            answer="Test answer",
            claims=[
                ExplanationClaim(
                    statement="Unsupported fact.",
                    claim_type="fact",
                    decision_fact_names=[
                        "imaginary_discount",
                    ],
                    evidence_ids=[],
                )
            ],
        )

        with self.assertRaisesRegex(
            ValueError,
            "unknown decision facts",
        ):
            validate_grounding(
                bundle=bundle,
                explanation=explanation,
            )

    @patch("reasoning.reasoner.boto3.client")
    def test_reasoner_returns_grounded_explanation(
        self,
        mock_boto_client,
    ):
        fake_client = FakeBedrockClient(
            VALID_EXPLANATION
        )
        mock_boto_client.return_value = fake_client

        reasoner = BedrockReasoner(
            model_id="test-model",
        )

        result = reasoner.explain(
            build_test_bundle()
        )

        self.assertIsInstance(
            result,
            GroundedExplanation,
        )

        self.assertEqual(
            len(result.claims),
            3,
        )

        self.assertEqual(
            result.claims[0].claim_type,
            "fact",
        )

        self.assertEqual(
            result.claims[2].claim_type,
            "inference",
        )

    @patch("reasoning.reasoner.boto3.client")
    def test_reasoner_sends_only_evidence_bundle(
        self,
        mock_boto_client,
    ):
        fake_client = FakeBedrockClient(
            VALID_EXPLANATION
        )
        mock_boto_client.return_value = fake_client

        reasoner = BedrockReasoner(
            model_id="test-model",
        )

        bundle = build_test_bundle()

        reasoner.explain(bundle)

        request = fake_client.last_request

        sent_text = (
            request["messages"][0]
            ["content"][0]["text"]
        )

        sent_bundle = json.loads(sent_text)

        self.assertEqual(
            sent_bundle,
            json.loads(
                bundle.model_dump_json()
            ),
        )

    @patch("reasoning.reasoner.boto3.client")
    def test_reasoner_rejects_invented_citation(
        self,
        mock_boto_client,
    ):
        invalid_response = {
            "answer": "Unsupported answer.",
            "claims": [
                {
                    "statement": "Invented claim.",
                    "claim_type": "fact",
                    "decision_fact_names": [],
                    "evidence_ids": [
                        "CALL-999",
                    ],
                }
            ],
        }

        mock_boto_client.return_value = (
            FakeBedrockClient(
                invalid_response
            )
        )

        reasoner = BedrockReasoner(
            model_id="test-model",
        )

        with self.assertRaisesRegex(
            ValueError,
            "unknown evidence IDs",
        ):
            reasoner.explain(
                build_test_bundle()
            )


if __name__ == "__main__":
    unittest.main()