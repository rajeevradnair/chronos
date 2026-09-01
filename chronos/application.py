from evidence_fusion.fixtures import (
    GLOBALHOTELS_DECISION_FACTS,
    GLOBALHOTELS_EVIDENCE,
)
from evidence_fusion.fusion import build_evidence_bundle
from query_enrichment.enricher import BedrockQueryEnricher
from reasoning.models import GroundedExplanation
from reasoning.reasoner import BedrockReasoner


class ChronosApplication:

    def __init__(
        self,
        enricher: BedrockQueryEnricher,
        reasoner: BedrockReasoner,
    ):
        self.enricher = enricher
        self.reasoner = reasoner

    def answer(
        self,
        question: str,
    ) -> GroundedExplanation:

        intent = self.enricher.enrich(question)

        # Release 1 retrieval remains fixture-based.
        # Future retrieval will consume this intent.
        _ = intent

        bundle = build_evidence_bundle(
            question=question,
            decision_facts=GLOBALHOTELS_DECISION_FACTS,
            evidence=GLOBALHOTELS_EVIDENCE,
        )

        return self.reasoner.explain(bundle)