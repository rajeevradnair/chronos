package com.chronos.cpq.approval;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "pricing_rules")
public class PricingRule {

    @Id
    private String id;

    private String name;

    private BigDecimal approvalThresholdPercent;

    private String requiredApproverRole;

    protected PricingRule() {
    }

    public PricingRule(
            String id,
            String name,
            BigDecimal approvalThresholdPercent,
            String requiredApproverRole) {

        this.id = id;
        this.name = name;
        this.approvalThresholdPercent = approvalThresholdPercent;
        this.requiredApproverRole = requiredApproverRole;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getApprovalThresholdPercent() {
        return approvalThresholdPercent;
    }

    public String getRequiredApproverRole() {
        return requiredApproverRole;
    }
}