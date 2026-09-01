from evidence.models import EvidenceRecord
from evidence_fusion.models import DecisionFact, EvidenceBundle


def deduplicate_evidence(
    evidence: list[EvidenceRecord],
) -> list[EvidenceRecord]:
    seen_hashes: set[str] = set()
    deduplicated: list[EvidenceRecord] = []

    for record in evidence:
        if record.content_hash in seen_hashes:
            continue

        seen_hashes.add(record.content_hash)
        deduplicated.append(record)

    return deduplicated


def build_evidence_bundle(
    question: str,
    decision_facts: list[DecisionFact],
    evidence: list[EvidenceRecord],
) -> EvidenceBundle:
    deduplicated_evidence = deduplicate_evidence(evidence)

    return EvidenceBundle(
        question=question,
        decision_facts=decision_facts,
        evidence=deduplicated_evidence,
    )