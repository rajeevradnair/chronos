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

---

# 2. Transactional Entities

Transactional entities represent business state, commercial actions, or formal decisions.

| Entity | Purpose | Authoritative Source |
|---|---|---|
| Opportunity | Commercial sales opportunity | CRM |
| Quote | Commercial proposal for an opportunity | CPQ |
| QuoteVersion | Specific historical version of a quote | CPQ |
| QuoteLine | Product/service line included in a quote | CPQ |
| DiscountRequest | Formal request for exceptional discounting | CPQ |
| Approval | Formal approval or rejection of a commercial exception | CPQ |
| SupportTicket | Formal customer support case | Support |

## GlobalHotels Examples

| Entity | Example |
|---|---|
| Opportunity | OPP-812 — GlobalHotels modernization deal |
| QuoteVersion | Quote v1 — January 25 |
| QuoteVersion | Quote v2 — February 15 |
| QuoteVersion | Quote v3 — February 28 |
| DiscountRequest | 17% discount request |
| Approval | VP approval on March 5 |
| SupportTicket | Customer/product issue associated with the opportunity |

## Ownership Examples

### Opportunity

CRM owns:

- opportunity ID
- customer/account relationship
- opportunity value
- stage
- forecast state
- final outcome

For example:

OPP-812
→ $3.8M opportunity
→ Closed Won

CRM is authoritative for those facts.

---

### Quote and QuoteVersion

CPQ owns:

- quote identity
- quote versions
- quote lines
- prices
- discounts
- commercial terms

For example:

Quote v3
→ submitted February 28
→ 17% discount

CPQ is authoritative for those facts.

---

### DiscountRequest and Approval

CPQ owns the formal commercial approval workflow.

For example:

17% discount request
→ requires executive exception
→ VP approves March 5

An email discussing approval may provide useful evidence, but the official Approval record remains authoritative for whether approval actually occurred.

---

### SupportTicket

The Support system owns formal support cases and their state.

For example:

Support records may show:

- issue reported
- issue status
- escalation
- resolution

An email mentioning the same problem does not replace the official SupportTicket.

## Important Rule

Transactional fact:

"VP approved the 17% discount."
→ authoritative source: CPQ Approval

Explanatory evidence:

"Why did the VP approve it?"
→ may require CRM + calls + email + policy + competitor evidence

Chronos must keep these two concepts separate.
