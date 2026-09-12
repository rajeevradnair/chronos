package com.chronos.crm.opportunity;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OpportunityHistoryService {

    private final OpportunityRepository opportunities;
    private final StageHistoryRepository stages;
    private final ForecastHistoryRepository forecasts;
    private final OwnerHistoryRepository owners;
    private final ValueHistoryRepository values;

    public OpportunityHistoryService(
            OpportunityRepository opportunities,
            StageHistoryRepository stages,
            ForecastHistoryRepository forecasts,
            OwnerHistoryRepository owners,
            ValueHistoryRepository values) {

        this.opportunities = opportunities;
        this.stages = stages;
        this.forecasts = forecasts;
        this.owners = owners;
        this.values = values;
    }

    public OpportunityAsOf findAsOf(
            String opportunityId,
            Instant asOf) {

        Opportunity opportunity = opportunities
                .findById(opportunityId)
                .orElseThrow();

        StageHistory stage = stages
                .findTopByOpportunityIdAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(
                        opportunityId,
                        asOf)
                .orElseThrow();

        ForecastHistory forecast = forecasts
                .findTopByOpportunityIdAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(
                        opportunityId,
                        asOf)
                .orElseThrow();

        OwnerHistory owner = owners
                .findTopByOpportunityIdAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(
                        opportunityId,
                        asOf)
                .orElseThrow();

        ValueHistory value = values
                .findTopByOpportunityIdAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(
                        opportunityId,
                        asOf)
                .orElseThrow();

        return new OpportunityAsOf(
                opportunity.getId(),
                opportunity.getName(),
                opportunity.getAccount().getId(),
                asOf,
                stage.getStage(),
                forecast.getProbability(),
                owner.getOwner(),
                value.getValue());
    }
}