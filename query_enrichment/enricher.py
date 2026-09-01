import json
import os

import boto3

from query_enrichment.models import StructuredQueryIntent


SYSTEM_PROMPT = """
You are the query-enrichment component of Chronos.

Convert an executive sales question into a StructuredQueryIntent.

Rules:
1. Extract only entities and filter values explicitly stated in the question.
2. Never invent account IDs, opportunity IDs, quote IDs, dates, percentages,
   product names, or other business facts.
3. If an entity is not stated, return null for that entity.
4. retrieval_targets describe information Chronos should obtain to answer
   the question.
5. Do not decide whether retrieval uses SQL, vector search, or graph traversal.
6. historical_boundary describes the business event around which Chronos
   should reconstruct historical evidence.
"""


class BedrockQueryEnricher:

    def __init__(
        self,
        model_id: str,
        region_name: str | None = None,
    ):
        self.model_id = model_id

        self.client = boto3.client(
            "bedrock-runtime",
            region_name=region_name
            or os.getenv("AWS_REGION", "us-west-2"),
        )

    def enrich(
        self,
        question: str,
    ) -> StructuredQueryIntent:

        schema = StructuredQueryIntent.model_json_schema()

        response = self.client.converse(
            modelId=self.model_id,
            system=[
                {
                    "text": SYSTEM_PROMPT,
                }
            ],
            messages=[
                {
                    "role": "user",
                    "content": [
                        {
                            "text": question,
                        }
                    ],
                }
            ],
            outputConfig={
                "textFormat": {
                    "type": "json_schema",
                    "structure": {
                        "jsonSchema": {
                            "name": "structured_query_intent",
                            "description": (
                                "Chronos structured query intent"
                            ),
                            "schema": json.dumps(schema),
                        }
                    },
                }
            },
            inferenceConfig={
                "maxTokens": 500,
                "temperature": 0,
            },
        )

        response_text = (
            response["output"]["message"]["content"][0]["text"]
        )

        return StructuredQueryIntent.model_validate_json(
            response_text
        )