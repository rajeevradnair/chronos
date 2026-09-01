import unittest

from chronos.application import ChronosApplication
from query_enrichment.models import (
    StructuredQueryIntent,
)
from reasoning.models import (
    ExplanationClaim,
    GroundedExplanation,
)


QUESTION = "Why did we approve 17% for GlobalHotels?"


class FakeEnricher:

    def __init__(self):
        self.received_question = None

    def enrich(self, question):
        self.received_question = question

        return StructuredQueryIntent(
            intent="decision_explanation",
            entities={
                "account": "GlobalHotels",
                "opportunity": None,
                "quote": None,
            },
            decision_type="discount_approval",
            historical_boundary="approval_time",
            retrieval_targets=[
                "quote",
                "discount_request",
                "approval",
                "pricing_policy",
                "customer_call",
            ],
            filters={
                "discount_percent": 17,
            },
        )


class FakeReasoner:

    def __init__(self):
        self.received_bundle = None

    def explain(self, bundle):
        self.received_bundle = bundle

        return GroundedExplanation(
            answer="Grounded test answer.",
            claims=[
                ExplanationClaim(
                    statement="The discount was 17%.",
                    claim_type="fact",
                    decision_fact_names=[
                        "discount_percent",
                    ],
                    evidence_ids=[],
                )
            ],
        )


class ChronosApplicationTests(unittest.TestCase):

    def test_application_runs_release_one_flow(self):
        enricher = FakeEnricher()
        reasoner = FakeReasoner()

        application = ChronosApplication(
            enricher=enricher,
            reasoner=reasoner,
        )

        result = application.answer(
            QUESTION
        )

        self.assertEqual(
            enricher.received_question,
            QUESTION,
        )

        self.assertEqual(
            reasoner.received_bundle.question,
            QUESTION,
        )

        self.assertEqual(
            len(
                reasoner.received_bundle.evidence
            ),
            3,
        )

        self.assertEqual(
            len(
                reasoner.received_bundle.decision_facts
            ),
            4,
        )

        self.assertEqual(
            result.answer,
            "Grounded test answer.",
        )


if __name__ == "__main__":
    unittest.main()