package com.chronos.knowledge.policy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pricing_policies")
public class PricingPolicy {

    @Id
    private String id;

    private String name;

    protected PricingPolicy() {
    }

    public PricingPolicy(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}