from typing import Literal

from pydantic import BaseModel, ConfigDict


Intent = Literal[
    "decision_explanation",
    "historical_state",
]

DecisionType = Literal[
    "discount_approval",
    "quote_submission",
    "forecast_change",
    "opportunity_outcome",
    "sales_commitment",
]

class QueryEntities(BaseModel):
    model_config = ConfigDict(extra="forbid")

    account: str | None = None
    opportunity: str | None = None
    quote: str | None = None

HistoricalBoundary = Literal[
    "approval_time",
    "quote_submission_time",
    "decision_time",
    "current_time",
]

RetrievalTarget = Literal[
    # Structured business facts
    "quote",
    "discount_request",
    "approval",

    # Searchable evidence
    "pricing_policy",
    "customer_call",
    "customer_email",
    "competitor_information",
    "product_documentation",
    "sales_playbook",
    "support_note",
]


class QueryFilters(BaseModel):
    model_config = ConfigDict(extra="forbid")

    discount_percent: float | None = None


class StructuredQueryIntent(BaseModel):
    model_config = ConfigDict(extra="forbid")

    intent: Intent
    entities: QueryEntities
    decision_type: DecisionType
    historical_boundary: HistoricalBoundary
    retrieval_targets: list[RetrievalTarget]
    filters: QueryFilters