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

---

# 3. Activity / Event Entities

Activity/event entities record things that happened over time.

They are useful because Chronos often needs to reconstruct the sequence leading to a decision.

| Entity | Purpose | Authoritative Source | Concrete Example |
|---|---|---|---|
| StageHistory | Records opportunity stage changes | CRM | OPP-812 moves from Negotiation to Commit |
| ForecastHistory | Records forecast/probability changes | CRM | OPP-812 forecast increases from 60% to 80% |
| Call | Records that a business conversation occurred | Conversation | CALL-44 — GlobalHotels pricing negotiation |
| EmailThread | Groups related email messages | Email | THREAD-103 — GlobalHotels pricing negotiation |
| TicketEvent | Records changes to a support case | Support | SUPPORT-91 escalated to engineering |

## Examples

### StageHistory

A StageHistory record answers:

"What stage was the opportunity in at a particular time?"

Example:

OPP-812
→ Negotiation
→ later moves to Commit

CRM owns this history.

---

### ForecastHistory

A ForecastHistory record answers:

"When did Sales change its confidence in the deal?"

Example:

OPP-812
→ forecast probability changes from 60% to 80%

CRM owns the official forecast history.

Chronos may later use calls, emails and quote activity to explain WHY the forecast changed.

---

### Call

A Call represents the business interaction itself.

Example:

CALL-44
→ GlobalHotels pricing negotiation call
→ February 8

The Conversation system owns the call record.

The call says that the meeting occurred.

The transcript provides evidence about what was said.

---

### EmailThread

An EmailThread groups related messages.

Example:

THREAD-103
→ negotiation between Sony Sales and GlobalHotels Procurement

The Email system owns the thread and its messages.

---

### TicketEvent

A TicketEvent records something that happened to an existing support case.

Example:

SUPPORT-91
→ escalated to engineering

The Support system owns the event.

---

# 4. Evidence Entities

Evidence entities contain information that can help explain a business decision.

Unlike a structured transaction such as a Quote or Approval, evidence often contains statements, reasoning, requirements, policies or knowledge.

| Entity | Purpose | Authoritative Source | Concrete Example |
|---|---|---|---|
| Transcript | What participants said during a call | Conversation | "Competitor pricing is materially lower." |
| EmailMessage | Written communication between participants | Email | Procurement requests revised pricing |
| PricingPolicy | Official pricing rules | Knowledge | POLICY-V2: discounts above 15% require VP approval |
| ProductDocument | Official product capability documentation | Knowledge | Requested capability is not currently supported |
| Battlecard | Internal competitive guidance | Knowledge | Guidance for competing against Competitor-X |
| Playbook | Internal Sales process/guidance | Knowledge | Guidance for enterprise discount negotiations |

## Examples

### Transcript

Example:

CALL-44 transcript:

"Competitor pricing is materially lower and we need Sony to improve the commercial offer."

This is evidence of competitive and pricing pressure.

Conversation is authoritative for the transcript.

---

### EmailMessage

Example:

EMAIL-103:

GlobalHotels Procurement requests revised pricing before its decision deadline.

This is evidence of negotiation pressure.

Email is authoritative for the original message.

---

### PricingPolicy

Example:

POLICY-V2:

Discounts above 15% require VP authorization.

This is authoritative business policy, not merely someone's opinion about the policy.

Knowledge is the authoritative source.

---

### ProductDocument

Example:

PROD-DOC-21:

The capability requested by GlobalHotels is not supported as of February 21.

This provides evidence of product reality at that historical point.

Knowledge is authoritative for the product documentation.

---

### Battlecard

Example:

BATTLECARD-X:

Internal guidance describes Competitor-X's pricing position and Sony's recommended competitive response.

This is useful evidence, but it is internal guidance rather than an official customer statement.

Knowledge owns the battlecard.

---

### Playbook

Example:

PLAYBOOK-7:

Sales guidance recommends escalation when strategic enterprise deals require exceptional commercial terms.

This is internal guidance.

Knowledge owns the playbook.

---

## Important Distinction

Chronos must not treat all evidence as equally authoritative.

For example:

POLICY-V2
→ official rule

CALL-44 transcript
→ evidence of what the customer said

BATTLECARD-X
→ internal competitive guidance

EMAIL-103
→ evidence of what procurement communicated

All are useful.

But they represent different kinds of truth.

---

# 5. Derived Chronos Entities

Derived entities are created by Chronos from authoritative business records and evidence.

They exist to make retrieval, relationship analysis and ranking possible.

They are not new sources of enterprise business truth.

| Entity | Purpose | Authoritative Owner | Concrete Example |
|---|---|---|---|
| EvidenceChunk | Searchable portion of a larger evidence document | Chronos | Paragraph from CALL-44 transcript describing competitor pricing pressure |
| Embedding | Vector representation used for semantic retrieval | Chronos | Vector representing "competitor pricing is materially lower" |
| GraphEdge | Derived relationship connecting canonical business/evidence entities | Chronos | CALL-44 → ABOUT → OPP-812 |
| RetrievalRanking | Query-specific ordering or score of retrieved evidence | Chronos | CALL-44 ranked above EMAIL-103 for a competitor-pressure query |

## Why These Derived Entities Exist

### EvidenceChunk

Source documents such as transcripts, emails and product documents may contain many different ideas.

Chronos needs smaller searchable units.

Example:

CALL-44
→ complete transcript

Chronos derives:

CHUNK-44-03
→ "Competitor pricing is materially lower and the customer is requesting improved pricing."

The original transcript remains authoritative.

The chunk is only a search representation.

---

### Embedding

Chronos later needs semantic retrieval.

Example:

CEO asks:

"Was competitive pressure driving the discount?"

The evidence may say:

"Another vendor's commercial proposal was substantially lower."

Those sentences do not share the same exact words.

An embedding helps Chronos recognize that they have similar meaning.

The embedding contains no new business fact.

It is a mathematical representation of existing evidence.

---

### GraphEdge

Chronos needs relationships that connect evidence to business context.

Example:

CALL-44
→ ABOUT
→ OPP-812

or:

QUOTE-V3
→ GOVERNED_BY
→ POLICY-V2

The graph representation allows Chronos to navigate those relationships.

The underlying entities still come from their authoritative systems.

For example:

OPP-812
→ CRM owns the Opportunity

QUOTE-V3
→ CPQ owns the Quote

POLICY-V2
→ Knowledge owns the Policy

Chronos owns only the derived relationship representation.

---

### RetrievalRanking

A query may retrieve several valid pieces of evidence.

Chronos therefore needs to record which evidence was considered most relevant for a particular question.

Example:

Question:

"Why was the 17% discount approved?"

Possible ranking:

1. CALL-44 — competitor pricing pressure
2. EMAIL-103 — procurement requested revised pricing
3. POLICY-V2 — >15% requires VP approval

The ranking is not enterprise truth.

It is a query-time Chronos result and may change for a different question.

---

# Source Ownership Summary

| Category | Entity | Authoritative Source |
|---|---|---|
| Master / Reference | Account | CRM |
| Master / Reference | Contact | CRM |
| Master / Reference | Product | CPQ |
| Master / Reference | SKU | CPQ |
| Master / Reference | PriceBook | CPQ |
| Transactional | Opportunity | CRM |
| Transactional | Quote | CPQ |
| Transactional | QuoteVersion | CPQ |
| Transactional | QuoteLine | CPQ |
| Transactional | DiscountRequest | CPQ |
| Transactional | Approval | CPQ |
| Transactional | SupportTicket | Support |
| Activity / Event | StageHistory | CRM |
| Activity / Event | ForecastHistory | CRM |
| Activity / Event | Call | Conversation |
| Activity / Event | EmailThread | Email |
| Activity / Event | TicketEvent | Support |
| Evidence | Transcript | Conversation |
| Evidence | EmailMessage | Email |
| Evidence | PricingPolicy | Knowledge |
| Evidence | ProductDocument | Knowledge |
| Evidence | Battlecard | Knowledge |
| Evidence | Playbook | Knowledge |
| Derived | EvidenceChunk | Chronos |
| Derived | Embedding | Chronos |
| Derived | GraphEdge | Chronos |
| Derived | RetrievalRanking | Chronos |

# Core Ownership Principle

Chronos separates:

business truth
from
search representation.

For example:

CRM
→ OPP-812 is a $3.8M opportunity

CPQ
→ Quote v3 has a 17% discount

Conversation
→ CALL-44 contains the customer's competitor statement

Knowledge
→ POLICY-V2 requires VP approval above 15%

Chronos
→ chunks, embeds, connects and ranks those records

Chronos may derive new representations of enterprise information, but it must preserve provenance back to the authoritative source.
