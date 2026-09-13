package com.chronos.cpq;

import com.chronos.cpq.catalog.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.chronos.cpq.quote.QuoteService;
import com.chronos.cpq.approval.ApprovalService;
import java.math.BigDecimal;

@SpringBootApplication
public class CpqApplication {

    public static void main(String[] args) {
        SpringApplication.run(CpqApplication.class, args);
    }


    @Bean
    CommandLineRunner seedCatalog(
            ProductRepository products,
            SkuRepository skus,
            PriceBookRepository prices,
            QuoteService quoteService, 
            ApprovalService approvalService) {

        return args -> {

            Product bravia = products.findById("PROD-BRAVIA")
                    .orElseGet(() -> products.save(
                            new Product(
                                    "PROD-BRAVIA",
                                    "BRAVIA Professional Display")));

            Sku bravia75 = skus.findById("SKU-FW75")
                    .orElseGet(() -> skus.save(
                            new Sku(
                                    "SKU-FW75",
                                    "FW-75BZ40L",
                                    bravia)));

            savePriceIfMissing(
                    prices,
                    "PRICE-FW75",
                    bravia75,
                    "2500.00");


            Product camera = products.findById("PROD-PTZ")
                    .orElseGet(() -> products.save(
                            new Product(
                                    "PROD-PTZ",
                                    "Sony PTZ Camera")));

            Sku cameraSku = skus.findById("SKU-PTZ")
                    .orElseGet(() -> skus.save(
                            new Sku(
                                    "SKU-PTZ",
                                    "SRG-A40",
                                    camera)));

            savePriceIfMissing(
                    prices,
                    "PRICE-PTZ",
                    cameraSku,
                    "4000.00");


            Product projector = products.findById("PROD-PROJECTOR")
                    .orElseGet(() -> products.save(
                            new Product(
                                    "PROD-PROJECTOR",
                                    "Sony Laser Projector")));

            Sku projectorSku = skus.findById("SKU-PROJECTOR")
                    .orElseGet(() -> skus.save(
                            new Sku(
                                    "SKU-PROJECTOR",
                                    "VPL-FHZ85",
                                    projector)));

            savePriceIfMissing(
                    prices,
                    "PRICE-PROJECTOR",
                    projectorSku,
                    "15000.00");


            Product integration = products.findById("PROD-INTEGRATION")
                    .orElseGet(() -> products.save(
                            new Product(
                                    "PROD-INTEGRATION",
                                    "Integration Services")));

            Sku integrationSku = skus.findById("SKU-INTEGRATION")
                    .orElseGet(() -> skus.save(
                            new Sku(
                                    "SKU-INTEGRATION",
                                    "INTEGRATION-SVC",
                                    integration)));

            savePriceIfMissing(
                    prices,
                    "PRICE-INTEGRATION",
                    integrationSku,
                    "20000.00");


            Product support = products.findById("PROD-SUPPORT")
                    .orElseGet(() -> products.save(
                            new Product(
                                    "PROD-SUPPORT",
                                    "Enterprise Support")));

            Sku supportSku = skus.findById("SKU-SUPPORT")
                    .orElseGet(() -> skus.save(
                            new Sku(
                                    "SKU-SUPPORT",
                                    "ENTERPRISE-SUPPORT",
                                    support)));

            savePriceIfMissing(
                    prices,
                    "PRICE-SUPPORT",
                    supportSku,
                    "450000.00");


            quoteService.createGlobalHotelsQuoteHistoryIfMissing();

                approvalService.createGlobalHotelsApprovalPathIfMissing();

        };
    }

    private void savePriceIfMissing(
            PriceBookRepository prices,
            String priceId,
            Sku sku,
            String unitPrice) {

        if (!prices.existsById(priceId)) {
            prices.save(
                    new PriceBook(
                            priceId,
                            sku,
                            new BigDecimal(unitPrice)));
        }
    }

}