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
The answer is a deterministic structured fact stored directly in CPQ.

---

## Q2 — Was the 17% discount formally approved, and by whom?

**Classification:** SQL

**Required systems:**
- CPQ / Approval

**Required facts/evidence:**
- DiscountRequest
- Approval record
- approval status
- approver

**Why:**  
This asks whether a specific business transaction occurred.

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
- customer pricing objections
- competitor pressure
- negotiation context
- applicable pricing policy
- VP approval

**Why:**  
Structured records tell us what happened, while cross-system evidence explains why the exception was justified.

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
- applicable pricing policy
- February 21 technical limitation
- exclusion of July 10 information

**Why:**  
Chronos must reconstruct only the facts and evidence available at that historical moment.

---

## Q5 — What commitments did Sales make to GlobalHotels?

**Classification:** Retrieval

**Required systems:**
- Conversation
- Email
- CPQ

**Required facts/evidence:**
- Sales statements
- promised capabilities
- delivery expectations
- support commitments
- quote language

**Why:**  
Commitments may appear across calls, emails, and proposal material rather than in one structured field.

---

## Q6 — Did Sales commit to functionality that the product did not support at that time?

**Classification:** Hybrid

**Required systems:**
- Conversation
- Email
- CPQ
- Knowledge / Product Documentation
- Technical Validation

**Required facts/evidence:**
- Sales commitments
- customer requirements
- Quote contents
- product capabilities at the time
- February 21 technical limitation
- later July 10 capability release

**Why:**  
Chronos must compare what the customer was told against what the product could actually deliver at that historical moment.

---

## Q7 — Could GlobalHotels reasonably have believed it was promised unsupported functionality?

**Classification:** Hybrid

**Required systems:**
- Conversation
- Email
- CPQ
- Knowledge / Product Documentation

**Required facts/evidence:**
- exact Sales representations
- customer questions and expectations
- proposal language
- product capability at the time
- later clarification of limitations

**Why:**  
The CEO cares not only about formal contract language, but also whether Sony created a reasonable customer expectation that it could not satisfy.

---

## Q8 — When did Sony first know that its commitments and product reality might be inconsistent?

**Classification:** Hybrid

**Required systems:**
- Conversation
- Email
- Knowledge / Product Documentation
- Technical Validation

**Required facts/evidence:**
- earlier Sales commitments
- technical validation timeline
- February 21 limitation discovery
- internal communications
- product documentation available at each point

**Why:**  
This establishes when the organization became aware of a possible expectation or commitment gap.

---

## Q9 — How did GlobalHotels react after the technical limitation became known?

**Classification:** Retrieval

**Required systems:**
- Conversation
- Email

**Required facts/evidence:**
- customer responses
- complaints
- disappointment
- trust concerns
- requests for remediation
- negotiation statements

**Why:**  
Customer reaction is primarily expressed in unstructured communications.

---

## Q10 — Did the capability gap become negotiation leverage for GlobalHotels?

**Classification:** Hybrid

**Required systems:**
- Conversation
- Email
- CPQ
- Knowledge / Product Documentation

**Required facts/evidence:**
- technical limitation
- customer reaction
- negotiation communications
- discount requests
- Quote v1 → v2 → v3 changes

**Why:**  
Chronos must connect evidence of customer dissatisfaction with subsequent commercial negotiation behavior.

---

## Q11 — Did perceived misrepresentation contribute to the final 17% discount?

**Classification:** Hybrid

**Required systems:**
- CRM
- CPQ
- Conversation
- Email
- Knowledge / Product Documentation
- Approval

**Required facts/evidence:**
- Sales commitments
- product limitation
- customer perception or complaint
- discount requests
- pricing progression
- approval justification
- final 17% discount

**Why:**  
This tests the CEO's central concern: whether a customer-experience failure ultimately translated into a financial concession.

---

## Q12 — How much did the discount increase after customer trust or capability concerns appeared?

**Classification:** Hybrid

**Required systems:**
- CPQ
- Conversation
- Email

**Required facts/evidence:**
- Quote v1 discount
- Quote v2 discount
- Quote v3 discount
- timing of capability concerns
- timing of customer complaints or objections

**Why:**  
SQL provides the pricing changes, while evidence is needed to determine whether those changes followed trust-related concerns.

---

## Q13 — Which current customers may have Sales commitments inconsistent with product or support reality?

**Classification:** Hybrid

**Required systems:**
- CRM
- CPQ
- Conversation
- Email
- Knowledge / Product Documentation
- Support

**Required facts/evidence:**
- active customer relationships
- Sales commitments
- quoted capabilities
- current product capabilities
- support limitations
- unresolved issues
- customer complaints

**Why:**  
This turns the GlobalHotels problem into a proactive enterprise-risk question.

---

## Q14 — Which large deals received deeper discounts after customer experience or expectation problems?

**Classification:** Hybrid

**Required systems:**
- CRM
- CPQ
- Conversation
- Email
- Support
- Knowledge / Product Documentation

**Required facts/evidence:**
- opportunity values
- discount history
- customer complaints
- expectation gaps
- product limitations
- support incidents
- negotiation evidence

**Why:**  
Chronos must identify whether customer-experience failures systematically translate into commercial concessions.

---

## Q15 — Which large deals were discounted primarily because of competitive pressure?

**Classification:** Hybrid

**Required systems:**
- CRM
- CPQ
- Conversation
- Email
- Competitive Intelligence

**Required facts/evidence:**
- opportunity values
- discount history
- competitor involvement
- customer competitor-pricing statements
- negotiation evidence

**Why:**  
This separates competitive discounting from discounts caused by Sony's own execution or customer-experience problems.

---

## Q16 — What recurring factors are causing margin erosion across major deals?

**Classification:** Hybrid

**Required systems:**
- CRM
- CPQ
- Conversation
- Email
- Competitive Intelligence
- Knowledge / Product Documentation
- Support
- Pricing Policy

**Required facts/evidence:**
- discount history
- opportunity values
- competitor pressure
- Sales commitment gaps
- product limitations
- customer trust concerns
- support failures
- pricing exceptions

**Why:**  
The CEO needs to distinguish recurring causes such as competition from preventable causes such as expectation gaps, unsupported commitments, and poor customer experience.
