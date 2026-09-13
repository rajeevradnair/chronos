package com.chronos.cpq.quote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteLineRepository
        extends JpaRepository<QuoteLine, String> {

    List<QuoteLine> findByQuoteVersionId(
            String quoteVersionId);
}