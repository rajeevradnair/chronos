package com.chronos.cpq;

import com.chronos.cpq.catalog.PriceBookRepository;
import com.chronos.cpq.catalog.ProductRepository;
import com.chronos.cpq.catalog.SkuRepository;
import com.chronos.cpq.quote.QuoteRepository;
import com.chronos.cpq.quote.QuoteResponse;
import com.chronos.cpq.quote.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:cpq",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CpqApplicationTests {

    @Autowired
    ProductRepository products;

    @Autowired
    SkuRepository skus;

    @Autowired
    PriceBookRepository prices;

    @Autowired
    QuoteRepository quotes;

    @Autowired
    QuoteService quoteService;

    @Test
    void seedsSonyCatalog() {

        assertThat(products.findById("PROD-BRAVIA"))
                .isPresent();

        assertThat(skus.findById("SKU-FW75"))
                .isPresent();

        var price = prices.findBySkuId("SKU-FW75")
                .orElseThrow();

        assertThat(price.getUnitPrice())
                .isEqualByComparingTo(new BigDecimal("2500.00"));
    }

    @Test
    @Transactional
    void createsGlobalHotelsQuoteV1() {

        var quote = quotes.findById("QUOTE-V1")
                .orElseThrow();

        assertThat(quote.getAccountId())
                .isEqualTo("ACC-1001");

        assertThat(quote.getOpportunityId())
                .isEqualTo("OPP-812");

        assertThat(quote.getLines())
                .hasSize(5);
    }

    @Test
    void quoteV1HasCorrectLineValues() {

        QuoteResponse quote =
                quoteService.findQuote("QUOTE-V1");

        assertThat(quote.lines())
                .hasSize(5);

        assertThat(quote.lines())
                .anySatisfy(line -> {

                    assertThat(line.skuId())
                            .isEqualTo("SKU-FW75");

                    assertThat(line.quantity())
                            .isEqualTo(300);

                    assertThat(line.unitPrice())
                            .isEqualByComparingTo(
                                    new BigDecimal("2500.00"));

                    assertThat(line.lineTotal())
                            .isEqualByComparingTo(
                                    new BigDecimal("750000.00"));
                });
    }

    @Test
    void quoteV1TotalsToThreePointEightMillion() {

        QuoteResponse quote =
                quoteService.findQuote("QUOTE-V1");

        assertThat(quote.total())
                .isEqualByComparingTo(
                        new BigDecimal("3800000.00"));
    }
}