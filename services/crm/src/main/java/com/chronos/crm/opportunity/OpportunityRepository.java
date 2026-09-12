package com.chronos.crm.opportunity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OpportunityRepository
        extends JpaRepository<Opportunity, String> {
}