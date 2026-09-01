from datetime import datetime
from typing import Literal

from pydantic import BaseModel, ConfigDict, Field


SourceSystem = Literal[
    "conversation",
    "email",
    "knowledge",
    "support",
]

SourceEntityType = Literal[
    "transcript",
    "email_message",
    "pricing_policy",
    "product_document",
    "battlecard",
    "playbook",
    "ticket_event",
]

DocumentClass = Literal[
    "customer_call",
    "customer_email",
    "pricing_policy",
    "product_documentation",
    "competitive_guidance",
    "sales_playbook",
    "support_note",
]

Authority = Literal[
    "official_policy",
    "official_product_documentation",
    "official_support_record",
    "customer_statement",
    "internal_guidance",
]

Classification = Literal[
    "internal",
    "confidential",
    "restricted",
]


class Provenance(BaseModel):
    model_config = ConfigDict(extra="forbid")

    source_system: SourceSystem
    source_entity_id: str = Field(min_length=1)
    parent_entity_id: str | None = None


class Permissions(BaseModel):
    model_config = ConfigDict(extra="forbid")

    classification: Classification
    allowed_roles: set[str]


class EmbeddingMetadata(BaseModel):
    model_config = ConfigDict(extra="forbid")

    model: str = Field(min_length=1)
    version: str = Field(min_length=1)
    dimensions: int = Field(ge=1)


class EvidenceRecord(BaseModel):
    model_config = ConfigDict(extra="forbid")

    evidence_id: str = Field(min_length=1)

    source_system: SourceSystem
    source_entity_type: SourceEntityType
    source_entity_id: str = Field(min_length=1)

    document_id: str = Field(min_length=1)
    chunk_id: str | None

    account_id: str | None = None
    opportunity_id: str | None = None
    quote_id: str | None = None
    quote_version_id: str | None = None

    document_class: DocumentClass
    text: str = Field(min_length=1)
    authority: Authority

    provenance: Provenance

    valid_from: datetime
    valid_to: datetime | None

    system_from: datetime
    system_to: datetime | None

    permissions: Permissions

    content_hash: str = Field(min_length=1)

    embedding_metadata: EmbeddingMetadata