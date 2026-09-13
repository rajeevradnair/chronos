package com.chronos.cpq.quote;

import com.chronos.cpq.catalog.PriceBook;
import com.chronos.cpq.catalog.PriceBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class QuoteService {

    private final QuoteRepository quotes;
    private final QuoteVersionRepository versions;
    private final PriceBookRepository prices;

    public QuoteService(
            QuoteRepository quotes,
            QuoteVersionRepository versions,
            PriceBookRepository prices) {

        this.quotes = quotes;
        this.versions = versions;
        this.prices = prices;
    }

    @Transactional
    public void createGlobalHotelsQuoteHistoryIfMissing() {

        Quote quote = quotes.findById("QUOTE-1001")
                .orElseGet(() ->
                        quotes.save(
                                new Quote(
                                        "QUOTE-1001",
                                        "ACC-1001",
                                        "OPP-812")));

        createVersionIfMissing(
                "QUOTE-V1",
                quote,
                1,
                Instant.parse("2026-01-25T00:00:00Z"),
                new BigDecimal("0.00"));

        createVersionIfMissing(
                "QUOTE-V2",
                quote,
                2,
                Instant.parse("2026-02-15T00:00:00Z"),
                new BigDecimal("0.10"));

        createVersionIfMissing(
                "QUOTE-V3",
                quote,
                3,
                Instant.parse("2026-02-28T00:00:00Z"),
                new BigDecimal("0.17"));
    }

    private void createVersionIfMissing(
            String versionId,
            Quote quote,
            int versionNumber,
            Instant createdAt,
            BigDecimal discountRate) {

        if (versions.existsById(versionId)) {
            return;
        }

        QuoteVersion version = new QuoteVersion(
                versionId,
                quote,
                versionNumber,
                createdAt);

        addDiscountedLine(
                version,
                versionId + "-LINE-1",
                "SKU-FW75",
                300,
                discountRate);

        addDiscountedLine(
                version,
                versionId + "-LINE-2",
                "SKU-PTZ",
                300,
                discountRate);

        addDiscountedLine(
                version,
                versionId + "-LINE-3",
                "SKU-PROJECTOR",
                40,
                discountRate);

        addDiscountedLine(
                version,
                versionId + "-LINE-4",
                "SKU-INTEGRATION",
                40,
                discountRate);

        addDiscountedLine(
                version,
                versionId + "-LINE-5",
                "SKU-SUPPORT",
                1,
                discountRate);

        versions.save(version);
    }

    private void addDiscountedLine(
            QuoteVersion version,
            String lineId,
            String skuId,
            int quantity,
            BigDecimal discountRate) {

        PriceBook price = prices.findBySkuId(skuId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No price found for SKU " + skuId));

        BigDecimal multiplier =
                BigDecimal.ONE.subtract(discountRate);

        BigDecimal discountedUnitPrice =
                price.getUnitPrice()
                        .multiply(multiplier);

        version.addLine(
                new QuoteLine(
                        lineId,
                        version,
                        skuId,
                        quantity,
                        discountedUnitPrice));
    }

    @Transactional(readOnly = true)
    public List<QuoteResponse> findVersions(
            String quoteId) {

        return versions
                .findByQuoteIdOrderByVersionNumber(quoteId)
                .stream()
                .map(QuoteResponse::from)
                .toList();
    }
}