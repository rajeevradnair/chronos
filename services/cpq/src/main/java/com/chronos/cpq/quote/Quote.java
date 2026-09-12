package com.chronos.cpq.quote;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    private String id;

    private String accountId;

    private String opportunityId;

    private Instant createdAt;

    @OneToMany(
            mappedBy = "quote",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<QuoteLine> lines = new ArrayList<>();

    protected Quote() {
    }

    public Quote(
            String id,
            String accountId,
            String opportunityId,
            Instant createdAt) {

        this.id = id;
        this.accountId = accountId;
        this.opportunityId = opportunityId;
        this.createdAt = createdAt;
    }

    public void addLine(QuoteLine line) {
        lines.add(line);
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<QuoteLine> getLines() {
        return lines;
    }
}