package com.chronos.cpq.quote;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record QuoteResponse(
        String quoteId,
        String quoteVersionId,
        int versionNumber,
        Instant createdAt,
        List<Line> lines,
        BigDecimal total) {

    public record Line(
            String skuId,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal lineTotal) {
    }

    public static QuoteResponse from(
            QuoteVersion version) {

        List<Line> lines =
                version.getLines()
                        .stream()
                        .map(line ->
                                new Line(
                                        line.getSkuId(),
                                        line.getQuantity(),
                                        line.getUnitPrice(),
                                        line.getLineTotal()))
                        .toList();

        BigDecimal total =
                lines.stream()
                        .map(Line::lineTotal)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add);

        return new QuoteResponse(
                version.getQuote().getId(),
                version.getId(),
                version.getVersionNumber(),
                version.getCreatedAt(),
                lines,
                total);
    }
}