from datetime import datetime
from typing import Any

from historical_reconstruction.temporal import is_valid_at


def reconstruct_as_of(
    evidence: list[dict[str, Any]],
    as_of: datetime,
) -> list[dict[str, Any]]:
    return [
        item
        for item in evidence
        if is_valid_at(
            valid_from=item["valid_from"],
            valid_to=item["valid_to"],
            as_of=as_of,
        )
    ]