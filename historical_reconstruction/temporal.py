from datetime import datetime


def is_valid_at(
    valid_from: datetime,
    valid_to: datetime | None,
    as_of: datetime,
) -> bool:
    if as_of < valid_from:
        return False

    if valid_to is not None and as_of >= valid_to:
        return False

    return True