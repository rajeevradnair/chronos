package com.chronos.knowledge.policy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface PricingPolicyVersionRepository
        extends JpaRepository<PricingPolicyVersion, String> {

    @Query("""
        select v
        from PricingPolicyVersion v
        where v.pricingPolicy.id = :policyId
          and v.validFrom <= :at
          and (v.validTo is null or :at < v.validTo)
        """)
    Optional<PricingPolicyVersion> findEffectiveAt(
            @Param("policyId") String policyId,
            @Param("at") Instant at);
}