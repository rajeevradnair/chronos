package com.chronos.cpq.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceBookRepository
        extends JpaRepository<PriceBook, String> {

    Optional<PriceBook> findBySkuId(String skuId);
}