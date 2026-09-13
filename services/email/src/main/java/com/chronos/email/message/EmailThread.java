package com.chronos.email.message;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_threads")
public class EmailThread {

    @Id
    private String id;

    private String accountId;
    private String opportunityId;
    private String subject;

    protected EmailThread() {
    }

    public EmailThread(
            String id,
            String accountId,
            String opportunityId,
            String subject) {

        this.id = id;
        this.accountId = accountId;
        this.opportunityId = opportunityId;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }
}