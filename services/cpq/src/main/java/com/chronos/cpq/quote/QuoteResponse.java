package com.chronos.cpq.quote;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record QuoteResponse(
        String quoteId,
        String accountId,
        String opportunityId,
        Instant createdAt,
        List<Line> lines,
        BigDecimal total) {

    public record Line(
            String skuId,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal lineTotal) {
    }

    public static QuoteResponse from(Quote quote) {

        List<Line> lines = quote.getLines()
                .stream()
                .map(line ->
                        new Line(
                                line.getSkuId(),
                                line.getQuantity(),
                                line.getUnitPrice(),
                                line.getLineTotal()))
                .toList();

        BigDecimal total = lines.stream()
                .map(Line::lineTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);

        return new QuoteResponse(
                quote.getId(),
                quote.getAccountId(),
                quote.getOpportunityId(),
                quote.getCreatedAt(),
                lines,
                total);
    }
}