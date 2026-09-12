from fastapi import FastAPI

app = FastAPI(title="Chronos Health Service")


@app.get("/health")
def health() -> dict[str, str]:
    return {
        "service": "chronos-health",
        "status": "ok",
    }