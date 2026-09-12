package com.chronos.cpq.catalog;

import jakarta.persistence.*;

@Entity
@Table(name = "skus")
public class Sku {

    @Id
    private String id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Sku() {
    }

    public Sku(String id, String code, Product product) {
        this.id = id;
        this.code = code;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Product getProduct() {
        return product;
    }
}