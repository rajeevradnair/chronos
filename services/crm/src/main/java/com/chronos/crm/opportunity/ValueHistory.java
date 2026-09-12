package com.chronos.crm.opportunity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "value_history")
public class ValueHistory {

    @Id
    private String id;

    private String opportunityId;

    @Column(name = "opportunity_value")
    private BigDecimal value;

    private Instant effectiveAt;

    protected ValueHistory() {
    }

    public ValueHistory(
            String id,
            String opportunityId,
            BigDecimal value,
            Instant effectiveAt) {

        this.id = id;
        this.opportunityId = opportunityId;
        this.value = value;
        this.effectiveAt = effectiveAt;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Instant getEffectiveAt() {
        return effectiveAt;
    }
}