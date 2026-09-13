package com.chronos.cpq.quote;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    private String id;

    private String accountId;

    private String opportunityId;

    protected Quote() {
    }

    public Quote(
            String id,
            String accountId,
            String opportunityId) {

        this.id = id;
        this.accountId = accountId;
        this.opportunityId = opportunityId;
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
}