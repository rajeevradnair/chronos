# Day 3 — Business Entity and Source Ownership Model

## Ownership Rule

Every business entity has one authoritative operational source.

The authoritative source is the system responsible for creating and maintaining the official business state of that entity.

Chronos may later copy or derive information into S3/Iceberg, Milvus, or Neptune, but those systems do not become the authoritative operational source.

Example:

CRM
→ authoritative Opportunity state

Milvus
→ searchable evidence derived from source records

Neptune
→ relationships derived from source records

S3/Iceberg
→ historical/replayable Chronos record

---

# Entity Categories

Chronos uses five entity categories:

1. Master / Reference
2. Transactional
3. Activity / Event
4. Evidence
5. Derived

---

# 1. Master / Reference Entities

These represent relatively stable business objects reused across many transactions.

| Entity | Purpose | Authoritative Source |
|---|---|---|
| Account | Customer or company being sold to | CRM |
| Contact | Person associated with an account or opportunity | CRM |
| Product | Commercial product family | CPQ |
| SKU | Specific sellable product configuration | CPQ |
| PriceBook | Official product pricing reference | CPQ |

## GlobalHotels Examples

| Entity | Example |
|---|---|
| Account | GlobalHotels Corp |
| Account ID | ACC-1001 |
| Product | BRAVIA Professional Display |
| Product | Sony PTZ Camera |
| Product | Laser Projector |
| Product | Integration Services |
| Product | Support Services |

## Important Rule

A master entity can appear in many other systems without changing ownership.

For example:

GlobalHotels may appear in:

- CRM
- CPQ
- emails
- call transcripts
- Chronos evidence
- Neptune

But:

**CRM remains authoritative for the Account.**
