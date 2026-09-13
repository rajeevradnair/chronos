package com.chronos.conversation.call;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "calls")
public class Call {

    @Id
    private String id;

    private String accountId;
    private String opportunityId;
    private Instant occurredAt;
    private String title;
    private String participants;

    protected Call() {
    }

    public Call(
            String id,
            String accountId,
            String opportunityId,
            Instant occurredAt,
            String title,
            String participants) {

        this.id = id;
        this.accountId = accountId;
        this.opportunityId = opportunityId;
        this.occurredAt = occurredAt;
        this.title = title;
        this.participants = participants;
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

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public String getTitle() {
        return title;
    }

    public String getParticipants() {
        return participants;
    }
}