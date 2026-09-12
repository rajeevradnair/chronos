package com.chronos.crm.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private String id;

    private String name;
    private String industry;

    protected Account() {
    }

    public Account(String id, String name, String industry) {
        this.id = id;
        this.name = name;
        this.industry = industry;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIndustry() {
        return industry;
    }
}