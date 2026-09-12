package com.chronos.crm.opportunity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "owner_history")
public class OwnerHistory {

    @Id
    private String id;

    private String opportunityId;

    private String owner;

    private Instant effectiveAt;

    protected OwnerHistory() {
    }

    public OwnerHistory(
            String id,
            String opportunityId,
            String owner,
            Instant effectiveAt) {

        this.id = id;
        this.opportunityId = opportunityId;
        this.owner = owner;
        this.effectiveAt = effectiveAt;
    }

    public String getOwner() {
        return owner;
    }

    public Instant getEffectiveAt() {
        return effectiveAt;
    }
}