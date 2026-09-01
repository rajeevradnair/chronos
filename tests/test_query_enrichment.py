import json
import unittest
from unittest.mock import patch

from pydantic import ValidationError

from query_enrichment.enricher import BedrockQueryEnricher
from query_enrichment.models import StructuredQueryIntent


VALID_INTENT = {
    "intent": "decision_explanation",
    "entities": {
        "account": "GlobalHotels",
        "opportunity": None,
        "quote": None,
    },
    "decision_type": "discount_approval",
    "historical_boundary": "approval_time",
    "retrieval_targets": [
        "quote",
        "discount_request",
        "approval",
        "pricing_policy",
        "customer_call",
        "competitor_information",
    ],
    "filters": {
        "discount_percent": 17,
    },
}


class FakeBedrockClient:

    def __init__(self):
        self.last_request = None

    def converse(self, **kwargs):
        self.last_request = kwargs

        return {
            "output": {
                "message": {
                    "content": [
                        {
                            "text": json.dumps(VALID_INTENT),
                        }
                    ]
                }
            }
        }


class QueryEnrichmentTests(unittest.TestCase):

    def test_structured_intent_accepts_valid_result(self):
        result = StructuredQueryIntent.model_validate(
            VALID_INTENT
        )

        self.assertEqual(
            result.entities.account,
            "GlobalHotels",
        )
        self.assertIsNone(result.entities.opportunity)
        self.assertIsNone(result.entities.quote)
        self.assertEqual(
            result.filters.discount_percent,
            17,
        )

    def test_structured_intent_rejects_unknown_retrieval_target(self):
        invalid = {
            **VALID_INTENT,
            "retrieval_targets": [
                "quote",
                "invented_target",
            ],
        }

        with self.assertRaises(ValidationError):
            StructuredQueryIntent.model_validate(
                invalid
            )

    def test_structured_intent_rejects_extra_fields(self):
        invalid = {
            **VALID_INTENT,
            "invented_business_fact": "OPP-812",
        }

        with self.assertRaises(ValidationError):
            StructuredQueryIntent.model_validate(
                invalid
            )

    @patch("query_enrichment.enricher.boto3.client")
    def test_enricher_returns_structured_intent(
        self,
        mock_boto_client,
    ):
        fake_client = FakeBedrockClient()
        mock_boto_client.return_value = fake_client

        enricher = BedrockQueryEnricher(
            model_id="test-model",
        )

        result = enricher.enrich(
            "Why did we approve 17% for GlobalHotels?"
        )

        self.assertEqual(
            result.intent,
            "decision_explanation",
        )
        self.assertEqual(
            result.entities.account,
            "GlobalHotels",
        )
        self.assertEqual(
            result.decision_type,
            "discount_approval",
        )
        self.assertEqual(
            result.historical_boundary,
            "approval_time",
        )
        self.assertEqual(
            result.filters.discount_percent,
            17,
        )

    @patch("query_enrichment.enricher.boto3.client")
    def test_enricher_sends_question_to_bedrock(
        self,
        mock_boto_client,
    ):
        fake_client = FakeBedrockClient()
        mock_boto_client.return_value = fake_client

        enricher = BedrockQueryEnricher(
            model_id="test-model",
        )

        question = (
            "Why did we approve 17% for GlobalHotels?"
        )

        enricher.enrich(question)

        request = fake_client.last_request

        self.assertEqual(
            request["modelId"],
            "test-model",
        )

        self.assertEqual(
            request["messages"][0]["content"][0]["text"],
            question,
        )

        self.assertEqual(
            request["inferenceConfig"]["temperature"],
            0,
        )


if __name__ == "__main__":
    unittest.main()