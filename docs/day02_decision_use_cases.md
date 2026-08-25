# Day 2 — CEO Decision-Intelligence Question Catalog

## Classification Rule

### SQL

Use SQL when the answer is a deterministic structured fact whose location is already known.

Example:

> What discount did Quote v3 contain?

The answer can be read directly from CPQ data.

---

### Retrieval

Use retrieval when the answer depends primarily on finding relevant explanatory evidence whose exact location is not known in advance.

Example:

> What objections did GlobalHotels raise during negotiations?

Relevant evidence may appear across calls and emails.

---

### Hybrid

Use hybrid when the question requires both:

1. deterministic structured business facts, and
2. explanatory evidence from calls, emails, policies, product documents, competitive intelligence, support records, or other sources.

Example:

> Why was the 17% GlobalHotels discount approved?

The 17% discount and approval are structured facts, but the reason requires additional evidence.

---

# Executive Questions

## Q1 — What discount did Quote v3 contain?

**Classification:** SQL

**Required systems:**
- CPQ

**Required facts/evidence:**
- Quote v3
- discount percentage

**Why:**  
The requested value is stored directly as structured quote data.

---

## Q2 — Was the 17% discount formally approved?

**Classification:** SQL

**Required systems:**
- CPQ / Approval

**Required facts/evidence:**
- DiscountRequest
- Approval record
- approval status
- approver

**Why:**  
This asks whether a specific transaction occurred, not why it occurred.

---

## Q3 — Why was the 17% GlobalHotels discount approved?

**Classification:** Hybrid

**Required systems:**
- CRM
- CPQ
- Conversation
- Email
- Knowledge / Pricing Policy
- Approval

**Required facts/evidence:**
- $3.8M opportunity value
- Quote v3 17% discount
- customer pricing objection
- competitor pricing pressure
- procurement negotiation context
- applicable pricing policy
- VP approval

**Why:**  
Structured records establish what happened, while calls, emails, and policy evidence explain why the exception was justified.

---

## Q4 — What did Sales know when Quote v3 was submitted?

**Classification:** Hybrid

**Required systems:**
- CRM
- CPQ
- Conversation
- Email
- Knowledge / Product Documentation

**Required facts/evidence:**
- Quote v3 submission date
- customer requirements known by February 28
- competitor information available by February 28
- pricing policy applicable by February 28
- technical limitation discovered by February 21
- exclusion of the July 10 capability release

**Why:**  
Chronos must combine structured historical state with evidence that was actually available before the historical boundary.

---

## Q5 — What pricing objections did GlobalHotels raise?

**Classification:** Retrieval

**Required systems:**
- Conversation
- Email

**Required facts/evidence:**
- discovery and negotiation call content
- customer pricing objections
- procurement messages
- statements about competing pricing

**Why:**  
The answer is contained primarily in unstructured conversations and messages rather than a known structured field.
