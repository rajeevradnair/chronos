package com.chronos.crm.opportunity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "forecast_history")
public class ForecastHistory {

    @Id
    private String id;

    private String opportunityId;

    private Integer probability;

    private Instant effectiveAt;

    protected ForecastHistory() {
    }

    public ForecastHistory(
            String id,
            String opportunityId,
            Integer probability,
            Instant effectiveAt) {

        this.id = id;
        this.opportunityId = opportunityId;
        this.probability = probability;
        this.effectiveAt = effectiveAt;
    }

    public Integer getProbability() {
        return probability;
    }

    public Instant getEffectiveAt() {
        return effectiveAt;
    }
}