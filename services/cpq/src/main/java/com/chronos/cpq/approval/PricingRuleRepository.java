package com.chronos.cpq.approval;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingRuleRepository
        extends JpaRepository<PricingRule, String> {
}