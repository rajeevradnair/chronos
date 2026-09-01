import os

from chronos.application import ChronosApplication
from query_enrichment.enricher import BedrockQueryEnricher
from reasoning.reasoner import BedrockReasoner


def main() -> None:
    model_id = os.environ["CHRONOS_BEDROCK_MODEL_ID"]

    application = ChronosApplication(
        enricher=BedrockQueryEnricher(
            model_id=model_id,
        ),
        reasoner=BedrockReasoner(
            model_id=model_id,
        ),
    )

    question = "Why did we approve 17% for GlobalHotels?"

    explanation = application.answer(question)

    print()
    print("QUESTION")
    print(question)

    print()
    print("ANSWER")
    print(explanation.answer)

    print()
    print("CLAIMS")

    for claim in explanation.claims:
        print()
        print(
            f"[{claim.claim_type.upper()}] "
            f"{claim.statement}"
        )

        if claim.decision_fact_names:
            print(
                "  Decision facts:",
                ", ".join(claim.decision_fact_names),
            )

        if claim.evidence_ids:
            print(
                "  Evidence:",
                ", ".join(claim.evidence_ids),
            )


if __name__ == "__main__":
    main()