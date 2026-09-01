import json
import os

import boto3

from evidence_fusion.models import EvidenceBundle
from reasoning.models import GroundedExplanation


SYSTEM_PROMPT = """
You are the reasoning component of Chronos, a Historical Decision
Intelligence system.

You receive a curated historical evidence bundle.

Use ONLY information contained in that bundle.

Rules:
1. Use claim_type "fact" when a statement is directly supported by
   the supplied decision facts or evidence.

2. Use claim_type "inference" when a statement is a conclusion drawn
   by combining or interpreting supplied information.

3. For every claim, identify its support using:
   - decision_fact_names for deterministic decision facts
   - evidence_ids for evidence records

4. Do not invent evidence IDs.

5. Do not invent decision fact names.

6. Do not introduce information not present in the bundle.

7. If the supplied information does not support a conclusion,
   do not make it.
"""


def validate_grounding(
    bundle: EvidenceBundle,
    explanation: GroundedExplanation,
) -> None:

    valid_evidence_ids = {
        record.evidence_id
        for record in bundle.evidence
    }

    valid_fact_names = {
        fact.name
        for fact in bundle.decision_facts
    }

    for claim in explanation.claims:

        unknown_evidence = (
            set(claim.evidence_ids)
            - valid_evidence_ids
        )

        if unknown_evidence:
            raise ValueError(
                "Explanation referenced unknown evidence IDs: "
                f"{sorted(unknown_evidence)}"
            )

        unknown_facts = (
            set(claim.decision_fact_names)
            - valid_fact_names
        )

        if unknown_facts:
            raise ValueError(
                "Explanation referenced unknown decision facts: "
                f"{sorted(unknown_facts)}"
            )


class BedrockReasoner:

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

    def explain(
        self,
        bundle: EvidenceBundle,
    ) -> GroundedExplanation:

        schema = GroundedExplanation.model_json_schema()

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
                            "text": bundle.model_dump_json(
                                indent=2,
                            )
                        }
                    ],
                }
            ],
            outputConfig={
                "textFormat": {
                    "type": "json_schema",
                    "structure": {
                        "jsonSchema": {
                            "name": "grounded_explanation",
                            "description": (
                                "A grounded historical decision explanation "
                                "with fact-vs-inference labeling and citations."
                            ),
                            "schema": json.dumps(schema),
                        }
                    },
                }
            },
            inferenceConfig={
                "maxTokens": 1000,
                "temperature": 0,
            },
        )

        response_text = (
            response["output"]["message"]["content"][0]["text"]
        )

        explanation = (
            GroundedExplanation.model_validate_json(
                response_text
            )
        )

        validate_grounding(
            bundle=bundle,
            explanation=explanation,
        )

        return explanation