import unittest
from unittest.mock import patch

from fastapi.testclient import TestClient
from pydantic import ValidationError

from query_enrichment.api import app
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


class QueryEnrichmentTests(unittest.TestCase):

    def test_structured_intent_accepts_valid_result(self):
        result = StructuredQueryIntent.model_validate(VALID_INTENT)

        self.assertEqual(result.entities.account, "GlobalHotels")
        self.assertIsNone(result.entities.opportunity)
        self.assertIsNone(result.entities.quote)
        self.assertEqual(result.filters.discount_percent, 17)

    def test_structured_intent_rejects_unknown_retrieval_target(self):
        invalid = {
            **VALID_INTENT,
            "retrieval_targets": ["quote", "invented_target"],
        }

        with self.assertRaises(ValidationError):
            StructuredQueryIntent.model_validate(invalid)

    def test_structured_intent_rejects_extra_fields(self):
        invalid = {
            **VALID_INTENT,
            "invented_business_fact": "OPP-812",
        }

        with self.assertRaises(ValidationError):
            StructuredQueryIntent.model_validate(invalid)

    @patch("query_enrichment.api.enrich_query")
    def test_enrich_endpoint_returns_structured_intent(self, mock_enrich):
        mock_enrich.return_value = StructuredQueryIntent.model_validate(
            VALID_INTENT
        )

        client = TestClient(app)

        response = client.post(
            "/enrich",
            json={
                "question": "Why did we approve 17% for GlobalHotels?"
            },
        )

        self.assertEqual(response.status_code, 200)

        body = response.json()

        self.assertEqual(body["entities"]["account"], "GlobalHotels")
        self.assertIsNone(body["entities"]["opportunity"])
        self.assertIsNone(body["entities"]["quote"])
        self.assertEqual(body["filters"]["discount_percent"], 17)

    @patch("query_enrichment.api.enrich_query")
    def test_invalid_request_does_not_call_bedrock(self, mock_enrich):
        client = TestClient(app)

        response = client.post("/enrich", json={})

        self.assertEqual(response.status_code, 422)
        mock_enrich.assert_not_called()


if __name__ == "__main__":
    unittest.main()