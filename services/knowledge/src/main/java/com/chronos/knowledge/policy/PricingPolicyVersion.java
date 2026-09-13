package com.chronos.knowledge.policy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "pricing_policy_versions")
public class PricingPolicyVersion {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricing_policy_id", nullable = false)
    private PricingPolicy pricingPolicy;

    private int version;

    private Instant validFrom;

    private Instant validTo;

    @Column(columnDefinition = "TEXT")
    private String content;

    protected PricingPolicyVersion() {
    }

    public PricingPolicyVersion(
            String id,
            PricingPolicy pricingPolicy,
            int version,
            Instant validFrom,
            Instant validTo,
            String content) {

        this.id = id;
        this.pricingPolicy = pricingPolicy;
        this.version = version;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public PricingPolicy getPricingPolicy() {
        return pricingPolicy;
    }

    public int getVersion() {
        return version;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public String getContent() {
        return content;
    }
}