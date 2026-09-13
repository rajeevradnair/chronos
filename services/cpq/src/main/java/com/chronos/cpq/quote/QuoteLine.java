package com.chronos.cpq.quote;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "quote_lines")
public class QuoteLine {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "quote_version_id", nullable = false)
    private QuoteVersion quoteVersion;

    private String skuId;

    private Integer quantity;

    private BigDecimal unitPrice;

    protected QuoteLine() {
    }

    public QuoteLine(
            String id,
            QuoteVersion quoteVersion,
            String skuId,
            Integer quantity,
            BigDecimal unitPrice) {

        this.id = id;
        this.quoteVersion = quoteVersion;
        this.skuId = skuId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public QuoteVersion getQuoteVersion() {
        return quoteVersion;
    }

    public String getSkuId() {
        return skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(
                BigDecimal.valueOf(quantity));
    }
}