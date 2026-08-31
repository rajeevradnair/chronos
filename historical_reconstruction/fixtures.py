from datetime import datetime, timezone


def utc(value: str) -> datetime:
    return datetime.fromisoformat(value).replace(tzinfo=timezone.utc)


GLOBALHOTELS_EVIDENCE = [
    {
        "evidence_id": "CALL-44",
        "document_class": "customer_call",
        "text": "Customer reported materially lower competitor pricing.",
        "valid_from": utc("2026-02-08T10:00:00"),
        "valid_to": None,
    },
    {
        "evidence_id": "POLICY-V2",
        "document_class": "pricing_policy",
        "text": "Discounts above 15% require VP approval.",
        "valid_from": utc("2026-02-12T00:00:00"),
        "valid_to": None,
    },
    {
        "evidence_id": "TECH-LIMITATION-1",
        "document_class": "product_documentation",
        "text": "The requested product capability is not currently supported.",
        "valid_from": utc("2026-02-21T09:00:00"),
        "valid_to": utc("2026-07-10T00:00:00"),
    },
    {
        "evidence_id": "CAPABILITY-RELEASE-1",
        "document_class": "product_documentation",
        "text": "The previously unavailable product capability is now supported.",
        "valid_from": utc("2026-07-10T00:00:00"),
        "valid_to": None,
    },
]