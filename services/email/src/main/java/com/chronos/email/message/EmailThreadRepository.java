package com.chronos.email.message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailThreadRepository
        extends JpaRepository<EmailThread, String> {

    List<EmailThread> findByOpportunityId(String opportunityId);
}