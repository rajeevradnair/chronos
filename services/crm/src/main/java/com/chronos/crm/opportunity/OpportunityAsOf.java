package com.chronos.crm.opportunity;

import java.math.BigDecimal;
import java.time.Instant;

public record OpportunityAsOf(
        String opportunityId,
        String name,
        String accountId,
        Instant asOf,
        String stage,
        Integer forecastProbability,
        String owner,
        BigDecimal value) {
}