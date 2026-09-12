package com.chronos.cpq.quote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteLineRepository
        extends JpaRepository<QuoteLine, String> {
}