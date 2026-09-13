package com.chronos.knowledge.policy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPolicyRepository
        extends JpaRepository<PricingPolicy, String> {
}