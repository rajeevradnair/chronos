from fastapi import FastAPI
from pydantic import BaseModel, ConfigDict, Field

from query_enrichment.enricher import enrich_query
from query_enrichment.models import StructuredQueryIntent


app = FastAPI(title="Chronos Query Enrichment")


class EnrichmentRequest(BaseModel):
    model_config = ConfigDict(extra="forbid")

    question: str = Field(min_length=1)


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok"}


@app.post("/enrich", response_model=StructuredQueryIntent)
def enrich(request: EnrichmentRequest) -> StructuredQueryIntent:
    return enrich_query(request.question)