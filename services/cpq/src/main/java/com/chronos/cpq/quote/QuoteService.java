package com.chronos.cpq.quote;

import com.chronos.cpq.catalog.PriceBook;
import com.chronos.cpq.catalog.PriceBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class QuoteService {

    private final QuoteRepository quotes;
    private final PriceBookRepository prices;

    public QuoteService(
            QuoteRepository quotes,
            PriceBookRepository prices) {

        this.quotes = quotes;
        this.prices = prices;
    }

    @Transactional
    public Quote createGlobalHotelsQuoteV1IfMissing() {

        return quotes.findById("QUOTE-V1")
                .orElseGet(this::createGlobalHotelsQuoteV1);
    }

    private Quote createGlobalHotelsQuoteV1() {

        Quote quote = new Quote(
                "QUOTE-V1",
                "ACC-1001",
                "OPP-812",
                Instant.parse("2026-01-25T00:00:00Z"));

        addLine(
                quote,
                "LINE-V1-1",
                "SKU-FW75",
                300);

        addLine(
                quote,
                "LINE-V1-2",
                "SKU-PTZ",
                300);

        addLine(
                quote,
                "LINE-V1-3",
                "SKU-PROJECTOR",
                40);

        addLine(
                quote,
                "LINE-V1-4",
                "SKU-INTEGRATION",
                40);

        addLine(
                quote,
                "LINE-V1-5",
                "SKU-SUPPORT",
                1);

        return quotes.save(quote);
    }

    private void addLine(
            Quote quote,
            String lineId,
            String skuId,
            int quantity) {

        PriceBook price = prices
                .findBySkuId(skuId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No price found for SKU " + skuId));

        quote.addLine(
                new QuoteLine(
                        lineId,
                        quote,
                        skuId,
                        quantity,
                        price.getUnitPrice()));
    }

    @Transactional(readOnly = true)
    public QuoteResponse findQuote(String quoteId) {

        Quote quote = quotes
                .findById(quoteId)
                .orElseThrow();

        return QuoteResponse.from(quote);
    }
}