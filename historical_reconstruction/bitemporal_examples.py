from historical_reconstruction.fixtures import CORRECTED_KNOWLEDGE, LATE_ARRIVING_POLICY, utc
from historical_reconstruction.temporal import is_bitemporally_visible


LATE_ARRIVING_POLICY_TRUTH_TABLE = [
    {
        "case": "before policy effective",
        "valid_at": utc("2026-02-10T12:00:00"),
        "known_at": utc("2026-02-15T12:00:00"),
        "expected": False,
    },
    {
        "case": "policy true but not yet known",
        "valid_at": utc("2026-02-13T12:00:00"),
        "known_at": utc("2026-02-13T12:00:00"),
        "expected": False,
    },
    {
        "case": "historical truth known later",
        "valid_at": utc("2026-02-13T12:00:00"),
        "known_at": utc("2026-02-15T12:00:00"),
        "expected": True,
    },
    {
        "case": "policy true and already known",
        "valid_at": utc("2026-02-15T12:00:00"),
        "known_at": utc("2026-02-15T12:00:00"),
        "expected": True,
    },
]


def evaluate_late_arriving_policy_truth_table():
    policy = LATE_ARRIVING_POLICY

    return [
        {
            **case,
            "actual": is_bitemporally_visible(
                valid_from=policy["valid_from"],
                valid_to=policy["valid_to"],
                system_from=policy["system_from"],
                system_to=policy["system_to"],
                valid_at=case["valid_at"],
                known_at=case["known_at"],
            ),
        }
        for case in LATE_ARRIVING_POLICY_TRUTH_TABLE
    ]

CORRECTED_KNOWLEDGE_TRUTH_TABLE = [
    {
        "case": "original knowledge before correction",
        "valid_at": utc("2026-02-08T12:00:00"),
        "known_at": utc("2026-02-10T12:00:00"),
        "expected_evidence_id": "COMPETITOR-PRICE-CLAIM-V1",
    },
    {
        "case": "corrected knowledge after correction",
        "valid_at": utc("2026-02-08T12:00:00"),
        "known_at": utc("2026-02-12T12:00:00"),
        "expected_evidence_id": "COMPETITOR-PRICE-CLAIM-V2",
    },
]


def evaluate_corrected_knowledge_truth_table():
    results = []

    for case in CORRECTED_KNOWLEDGE_TRUTH_TABLE:
        visible = [
            item["evidence_id"]
            for item in CORRECTED_KNOWLEDGE
            if is_bitemporally_visible(
                valid_from=item["valid_from"],
                valid_to=item["valid_to"],
                system_from=item["system_from"],
                system_to=item["system_to"],
                valid_at=case["valid_at"],
                known_at=case["known_at"],
            )
        ]

        results.append({
            **case,
            "actual_evidence_ids": visible,
        })

    return results