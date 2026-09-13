package com.chronos.cpq.approval;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRequestRepository
        extends JpaRepository<DiscountRequest, String> {

    Optional<DiscountRequest> findByQuoteVersionId(
            String quoteVersionId);
}