from evidence.models import EvidenceRecord
from evidence_fusion.models import DecisionFact


GLOBALHOTELS_DECISION_FACTS = [
    DecisionFact(
        name="opportunity_value",
        value=3_800_000,
        source="CRM",
    ),
    DecisionFact(
        name="quote_version",
        value="v3",
        source="CPQ",
    ),
    DecisionFact(
        name="discount_percent",
        value=17,
        source="CPQ",
    ),
    DecisionFact(
        name="approval_status",
        value="approved",
        source="CPQ",
    ),
]


CALL_44 = EvidenceRecord.model_validate(
    {
        "evidence_id": "CALL-44",
        "source_system": "conversation",
        "source_entity_type": "transcript",
        "source_entity_id": "CALL-44",
        "document_id": "CALL-44",
        "chunk_id": "CALL-44-CHUNK-1",
        "account_id": "ACC-1001",
        "opportunity_id": "OPP-812",
        "quote_id": None,
        "quote_version_id": None,
        "document_class": "customer_call",
        "text": "Customer reported materially lower competitor pricing.",
        "authority": "customer_statement",
        "provenance": {
            "source_system": "conversation",
            "source_entity_id": "CALL-44",
            "parent_entity_id": None,
        },
        "valid_from": "2026-02-08T10:00:00Z",
        "valid_to": None,
        "system_from": "2026-02-08T10:00:00Z",
        "system_to": None,
        "permissions": {
            "classification": "confidential",
            "allowed_roles": ["sales", "executive"],
        },
        "content_hash": "hash-call-44-chunk-1",
        "embedding_metadata": {
            "model": "fixture-embedding",
            "version": "1",
            "dimensions": 3,
        },
    }
)


EMAIL_103 = EvidenceRecord.model_validate(
    {
        "evidence_id": "EMAIL-103",
        "source_system": "email",
        "source_entity_type": "email_message",
        "source_entity_id": "EMAIL-103",
        "document_id": "EMAIL-103",
        "chunk_id": None,
        "account_id": "ACC-1001",
        "opportunity_id": "OPP-812",
        "quote_id": "QUOTE-V3",
        "quote_version_id": "QUOTE-V3",
        "document_class": "customer_email",
        "text": "Procurement requested revised pricing before the decision deadline.",
        "authority": "customer_statement",
        "provenance": {
            "source_system": "email",
            "source_entity_id": "EMAIL-103",
            "parent_entity_id": None,
        },
        "valid_from": "2026-02-26T14:00:00Z",
        "valid_to": None,
        "system_from": "2026-02-26T14:00:00Z",
        "system_to": None,
        "permissions": {
            "classification": "confidential",
            "allowed_roles": ["sales", "executive"],
        },
        "content_hash": "hash-email-103",
        "embedding_metadata": {
            "model": "fixture-embedding",
            "version": "1",
            "dimensions": 3,
        },
    }
)


POLICY_V2 = EvidenceRecord.model_validate(
    {
        "evidence_id": "POLICY-V2",
        "source_system": "knowledge",
        "source_entity_type": "pricing_policy",
        "source_entity_id": "POLICY-V2",
        "document_id": "POLICY-V2",
        "chunk_id": None,
        "account_id": None,
        "opportunity_id": None,
        "quote_id": None,
        "quote_version_id": None,
        "document_class": "pricing_policy",
        "text": "Discounts above 15% require VP approval.",
        "authority": "official_policy",
        "provenance": {
            "source_system": "knowledge",
            "source_entity_id": "POLICY-V2",
            "parent_entity_id": None,
        },
        "valid_from": "2026-02-12T00:00:00Z",
        "valid_to": None,
        "system_from": "2026-02-12T00:00:00Z",
        "system_to": None,
        "permissions": {
            "classification": "internal",
            "allowed_roles": ["sales", "executive"],
        },
        "content_hash": "hash-policy-v2",
        "embedding_metadata": {
            "model": "fixture-embedding",
            "version": "1",
            "dimensions": 3,
        },
    }
)


GLOBALHOTELS_EVIDENCE = [
    CALL_44,
    CALL_44.model_copy(deep=True),  # intentional duplicate
    EMAIL_103,
    POLICY_V2,
]