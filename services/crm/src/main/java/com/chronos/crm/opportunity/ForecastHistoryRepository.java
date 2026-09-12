package com.chronos.crm.opportunity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface ForecastHistoryRepository
        extends JpaRepository<ForecastHistory, String> {

    Optional<ForecastHistory>
    findTopByOpportunityIdAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(
            String opportunityId,
            Instant asOf);
}