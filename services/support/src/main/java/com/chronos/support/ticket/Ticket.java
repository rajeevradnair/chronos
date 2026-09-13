package com.chronos.support.ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    private String id;

    private String accountId;
    private String opportunityId;
    private String title;
    private String status;
    private Instant openedAt;

    protected Ticket() {
    }

    public Ticket(
            String id,
            String accountId,
            String opportunityId,
            String title,
            String status,
            Instant openedAt) {

        this.id = id;
        this.accountId = accountId;
        this.opportunityId = opportunityId;
        this.title = title;
        this.status = status;
        this.openedAt = openedAt;
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

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public Instant getOpenedAt() {
        return openedAt;
    }
}