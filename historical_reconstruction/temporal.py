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

def is_bitemporally_visible(
    valid_from: datetime,
    valid_to: datetime | None,
    system_from: datetime,
    system_to: datetime | None,
    valid_at: datetime,
    known_at: datetime,
) -> bool:
    return (
        is_valid_at(valid_from, valid_to, valid_at)
        and is_valid_at(system_from, system_to, known_at)
    )