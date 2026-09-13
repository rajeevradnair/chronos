package com.chronos.conversation.call;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CallRepository
        extends JpaRepository<Call, String> {

    List<Call> findByOpportunityIdOrderByOccurredAt(
            String opportunityId);
}