package com.chronos.cpq.quote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteVersionRepository
        extends JpaRepository<QuoteVersion, String> {

    List<QuoteVersion> findByQuoteIdOrderByVersionNumber(
            String quoteId);
}