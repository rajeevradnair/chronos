package com.chronos.cpq.catalog;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "price_book")
public class PriceBook {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "sku_id", nullable = false)
    private Sku sku;

    private BigDecimal unitPrice;

    protected PriceBook() {
    }

    public PriceBook(
            String id,
            Sku sku,
            BigDecimal unitPrice) {

        this.id = id;
        this.sku = sku;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public Sku getSku() {
        return sku;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}