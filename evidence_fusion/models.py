from typing import Any

from pydantic import BaseModel, ConfigDict, Field

from evidence.models import EvidenceRecord


class DecisionFact(BaseModel):
    model_config = ConfigDict(extra="forbid")

    name: str = Field(min_length=1)
    value: Any
    source: str = Field(min_length=1)


class EvidenceBundle(BaseModel):
    model_config = ConfigDict(extra="forbid")

    question: str = Field(min_length=1)
    decision_facts: list[DecisionFact]
    evidence: list[EvidenceRecord]