package com.chronos.crm.opportunity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "stage_history")
public class StageHistory {

    @Id
    private String id;

    private String opportunityId;

    private String stage;

    private Instant effectiveAt;

    protected StageHistory() {
    }

    public StageHistory(
            String id,
            String opportunityId,
            String stage,
            Instant effectiveAt) {

        this.id = id;
        this.opportunityId = opportunityId;
        this.stage = stage;
        this.effectiveAt = effectiveAt;
    }

    public String getStage() {
        return stage;
    }

    public Instant getEffectiveAt() {
        return effectiveAt;
    }
}