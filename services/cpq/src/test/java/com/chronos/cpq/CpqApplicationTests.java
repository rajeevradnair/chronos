package com.chronos.cpq;

import com.chronos.cpq.quote.QuoteVersion;
import java.util.List;
import com.chronos.cpq.catalog.PriceBookRepository;
import com.chronos.cpq.catalog.ProductRepository;
import com.chronos.cpq.catalog.SkuRepository;
import com.chronos.cpq.quote.QuoteLineRepository;
import com.chronos.cpq.quote.QuoteRepository;
import com.chronos.cpq.quote.QuoteResponse;
import com.chronos.cpq.quote.QuoteService;
import com.chronos.cpq.quote.QuoteVersionRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import com.chronos.cpq.approval.*;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

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
        QuoteVersionRepository quoteVersions;

        @Autowired
        QuoteLineRepository quoteLines;

        @Autowired
        QuoteService quoteService;

        @Autowired
        private PricingRuleRepository pricingRules;

        @Autowired
        private DiscountRequestRepository discountRequests;

        @Autowired
        private ApprovalRepository approvals;

        @Autowired
        private ApprovalService approvalService;

    @Test
    void seedsSonyCatalog() {

        assertThat(products.findById("PROD-BRAVIA"))
                .isPresent();

        assertThat(skus.findById("SKU-FW75"))
                .isPresent();

        var price = prices.findBySkuId("SKU-FW75")
                .orElseThrow();

        assertThat(price.getUnitPrice())
                .isEqualByComparingTo(
                        new BigDecimal("2500.00"));
    }


    @Test
    void createsSingleCommercialQuote() {

        assertThat(quotes.count())
                .isEqualTo(1);

        var quote = quotes.findById("QUOTE-1001")
                .orElseThrow();

        assertThat(quote.getAccountId())
                .isEqualTo("ACC-1001");

        assertThat(quote.getOpportunityId())
                .isEqualTo("OPP-812");
    }


    @Test
    void generatesThreeQuoteVersions() {

        var versions =
                quoteService.findVersions("QUOTE-1001");

        assertThat(versions)
                .hasSize(3);

        assertThat(versions.get(0).quoteVersionId())
                .isEqualTo("QUOTE-V1");

        assertThat(versions.get(0).versionNumber())
                .isEqualTo(1);

        assertThat(versions.get(1).quoteVersionId())
                .isEqualTo("QUOTE-V2");

        assertThat(versions.get(1).versionNumber())
                .isEqualTo(2);

        assertThat(versions.get(2).quoteVersionId())
                .isEqualTo("QUOTE-V3");

        assertThat(versions.get(2).versionNumber())
                .isEqualTo(3);
    }


    @Test
    void preservesQuoteVersionDates() {

        var versions =
                quoteService.findVersions("QUOTE-1001");

        var v1 = versions.get(0);
        var v2 = versions.get(1);
        var v3 = versions.get(2);

        assertThat(v1.createdAt())
                .isEqualTo(
                        Instant.parse(
                                "2026-01-25T00:00:00Z"));

        assertThat(v2.createdAt())
                .isEqualTo(
                        Instant.parse(
                                "2026-02-15T00:00:00Z"));

        assertThat(v3.createdAt())
                .isEqualTo(
                        Instant.parse(
                                "2026-02-28T00:00:00Z"));
    }


    @Test
    void everyQuoteVersionHasFiveLines() {

        var versions =
                quoteService.findVersions("QUOTE-1001");

        assertThat(versions)
                .hasSize(3);

        for (var version : versions) {

            assertThat(version.lines())
                    .hasSize(5);
        }

        assertThat(quoteLines.count())
                .isEqualTo(15);
    }


    @Test
    void quoteLinesBelongToSpecificVersions() {

        assertThat(
                quoteLines.findByQuoteVersionId(
                        "QUOTE-V1"))
                .hasSize(5);

        assertThat(
                quoteLines.findByQuoteVersionId(
                        "QUOTE-V2"))
                .hasSize(5);

        assertThat(
                quoteLines.findByQuoteVersionId(
                        "QUOTE-V3"))
                .hasSize(5);
    }


    @Test
    void calculatesEveryLineFromQuantityAndUnitPrice() {

        var versions =
                quoteService.findVersions("QUOTE-1001");

        for (var version : versions) {

            for (QuoteResponse.Line line
                    : version.lines()) {

                BigDecimal expected =
                        line.unitPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                line.quantity()));

                assertThat(line.lineTotal())
                        .isEqualByComparingTo(
                                expected);
            }
        }
    }


    @Test
    void preservesEarlierQuoteVersions() {

        var versions =
                quoteService.findVersions("QUOTE-1001");

        var v1 = versions.get(0);
        var v2 = versions.get(1);
        var v3 = versions.get(2);

        assertThat(v1.total())
                .isGreaterThan(v2.total());

        assertThat(v2.total())
                .isGreaterThan(v3.total());
    }


    @Test
    void quoteHistoryGenerationIsIdempotent() {

        quoteService
                .createGlobalHotelsQuoteHistoryIfMissing();

        quoteService
                .createGlobalHotelsQuoteHistoryIfMissing();

        assertThat(quotes.count())
                .isEqualTo(1);

        assertThat(quoteVersions.count())
                .isEqualTo(3);

        assertThat(quoteLines.count())
                .isEqualTo(15);
    }

        @Test
        void createsSeventeenPercentApprovalPath() {

                ApprovalPathResponse path =
                        approvalService.findApprovalPath("QUOTE-V3");

                assertEquals(
                        new BigDecimal("17.00"),
                        path.requestedPercent());

                assertEquals(
                        new BigDecimal("15.00"),
                        path.approvalThresholdPercent());

                assertTrue(
                        path.exceptionRequired());

                assertEquals(
                        "VP",
                        path.requiredApproverRole());

                assertEquals(
                        "APPROVED",
                        path.approvalStatus());

                assertEquals(
                        "VP",
                        path.approverRole());

                assertEquals(
                        "CPQ_EXCEPTION_WORKFLOW",
                        path.approvalSource());
        }

        @Test
        void approvalPathCreationIsIdempotent() {

                approvalService.createGlobalHotelsApprovalPathIfMissing();
                approvalService.createGlobalHotelsApprovalPathIfMissing();

                assertEquals(1, pricingRules.count());
                assertEquals(1, discountRequests.count());
                assertEquals(1, approvals.count());
        }

        @Test
        void globalHotelsHasThreeQuoteVersions() {

                List<QuoteVersion> versions =
                        quoteVersions
                                .findByQuoteIdOrderByVersionNumber("QUOTE-1001");

                assertEquals(3, versions.size());

                assertEquals("QUOTE-V1", versions.get(0).getId());
                assertEquals("QUOTE-V2", versions.get(1).getId());
                assertEquals("QUOTE-V3", versions.get(2).getId());

                assertEquals(1, versions.get(0).getVersionNumber());
                assertEquals(2, versions.get(1).getVersionNumber());
                assertEquals(3, versions.get(2).getVersionNumber());
        }

        @Test
        void eachQuoteVersionOwnsFiveLines() {

                assertEquals(
                        5,
                        quoteLines
                                .findByQuoteVersionId("QUOTE-V1")
                                .size());

                assertEquals(
                        5,
                        quoteLines
                                .findByQuoteVersionId("QUOTE-V2")
                                .size());

                assertEquals(
                        5,
                        quoteLines
                                .findByQuoteVersionId("QUOTE-V3")
                                .size());
        }

        @Test
        void quoteVersionsPreserveHistoricalTotals() {

        List<QuoteResponse> versions =
                quoteService.findVersions("QUOTE-1001");

            assertEquals(
            0,
            versions.get(0).total()
                    .compareTo(new BigDecimal("3800000")));

                assertEquals(
                        0,
                        versions.get(1).total()
                                .compareTo(new BigDecimal("3420000")));

                assertEquals(
                        0,
                        versions.get(2).total()
                                .compareTo(new BigDecimal("3154000")));
        }

        @Test
        void seventeenPercentRequiresVpException() {

        ApprovalPathResponse path =
                approvalService.findApprovalPath("QUOTE-V3");

        assertEquals(
                0,
                path.requestedPercent()
                        .compareTo(new BigDecimal("17")));

        assertEquals(
                0,
                path.approvalThresholdPercent()
                        .compareTo(new BigDecimal("15")));

        assertTrue(path.exceptionRequired());

        assertEquals(
                "VP",
                path.requiredApproverRole());
        }

        @Test
        void approvalRetainsProvenance() {

        ApprovalPathResponse path =
                approvalService.findApprovalPath("QUOTE-V3");

        assertEquals(
                "DISCOUNT-17",
                path.discountRequestId());

        assertEquals(
                "Alex Morgan",
                path.requestedBy());

        assertEquals(
                "Competitive pricing pressure",
                path.requestReason());

        assertEquals(
                "APPROVAL-88",
                path.approvalId());

        assertEquals(
                "APPROVED",
                path.approvalStatus());

        assertEquals(
                "Dana Brooks",
                path.approvedBy());

        assertEquals(
                "VP",
                path.approverRole());

        assertEquals(
                "CPQ_EXCEPTION_WORKFLOW",
                path.approvalSource());
        }

        @Test
        void discountRequestWithoutApprovalIsPending() {

        QuoteVersion v2 =
                quoteVersions.findById("QUOTE-V2")
                        .orElseThrow();

        PricingRule rule =
                pricingRules.findById("RULE-VP-15")
                        .orElseThrow();

        discountRequests.save(
                new DiscountRequest(
                        "TEST-PENDING-DISCOUNT",
                        v2,
                        rule,
                        new BigDecimal("16.00"),
                        "Test User",
                        Instant.parse("2026-02-16T00:00:00Z"),
                        "Pending approval test"));

        ApprovalPathResponse path =
                approvalService.findApprovalPath("QUOTE-V2");

        assertTrue(path.exceptionRequired());

        assertEquals(
                "PENDING",
                path.approvalStatus());

        assertNull(path.approvalId());
        assertNull(path.approvedBy());
        assertNull(path.approvedAt());
        }


}