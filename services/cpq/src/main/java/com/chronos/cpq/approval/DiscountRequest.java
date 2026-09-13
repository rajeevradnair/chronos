package com.chronos.cpq.approval;

import com.chronos.cpq.quote.QuoteVersion;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "discount_requests")
public class DiscountRequest {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "quote_version_id", nullable = false)
    private QuoteVersion quoteVersion;

    @ManyToOne
    @JoinColumn(name = "pricing_rule_id", nullable = false)
    private PricingRule pricingRule;

    private BigDecimal requestedPercent;

    private String requestedBy;

    private Instant requestedAt;

    private String reason;

    protected DiscountRequest() {
    }

    public DiscountRequest(
            String id,
            QuoteVersion quoteVersion,
            PricingRule pricingRule,
            BigDecimal requestedPercent,
            String requestedBy,
            Instant requestedAt,
            String reason) {

        this.id = id;
        this.quoteVersion = quoteVersion;
        this.pricingRule = pricingRule;
        this.requestedPercent = requestedPercent;
        this.requestedBy = requestedBy;
        this.requestedAt = requestedAt;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public QuoteVersion getQuoteVersion() {
        return quoteVersion;
    }

    public PricingRule getPricingRule() {
        return pricingRule;
    }

    public BigDecimal getRequestedPercent() {
        return requestedPercent;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public String getReason() {
        return reason;
    }
}