from typing import Literal

from pydantic import BaseModel, ConfigDict, Field


ClaimType = Literal[
    "fact",
    "inference",
]


class ExplanationClaim(BaseModel):
    model_config = ConfigDict(extra="forbid")

    statement: str = Field(min_length=1)
    claim_type: ClaimType

    decision_fact_names: list[str]
    evidence_ids: list[str]


class GroundedExplanation(BaseModel):
    model_config = ConfigDict(extra="forbid")

    answer: str = Field(min_length=1)
    claims: list[ExplanationClaim] = Field(min_length=1)