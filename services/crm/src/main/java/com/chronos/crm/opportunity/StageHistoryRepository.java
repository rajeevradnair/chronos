package com.chronos.crm.opportunity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface StageHistoryRepository
        extends JpaRepository<StageHistory, String> {

    Optional<StageHistory>
    findTopByOpportunityIdAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(
            String opportunityId,
            Instant asOf);
}