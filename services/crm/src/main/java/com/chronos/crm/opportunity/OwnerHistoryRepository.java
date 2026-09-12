package com.chronos.crm.opportunity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface OwnerHistoryRepository
        extends JpaRepository<OwnerHistory, String> {

    Optional<OwnerHistory>
    findTopByOpportunityIdAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(
            String opportunityId,
            Instant asOf);
}