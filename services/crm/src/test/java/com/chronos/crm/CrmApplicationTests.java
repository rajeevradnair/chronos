package com.chronos.crm;

import com.chronos.crm.opportunity.OpportunityAsOf;
import com.chronos.crm.opportunity.OpportunityHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:crm",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CrmApplicationTests {

    @Autowired
    private OpportunityHistoryService historyService;

    @Test
    void reconstructsJanuaryState() {

        OpportunityAsOf state = historyService.findAsOf(
                "OPP-812",
                Instant.parse("2026-01-20T00:00:00Z"));

        assertThat(state.stage())
                .isEqualTo("Prospecting");

        assertThat(state.forecastProbability())
                .isEqualTo(40);

        assertThat(state.owner())
                .isEqualTo("Jordan Lee");

        assertThat(state.value())
                .isEqualByComparingTo(new BigDecimal("3800000"));
    }

    @Test
    void reconstructsFebruaryStateWithoutFutureLeakage() {

        OpportunityAsOf state = historyService.findAsOf(
                "OPP-812",
                Instant.parse("2026-02-20T00:00:00Z"));

        assertThat(state.stage())
                .isEqualTo("Qualification");

        assertThat(state.forecastProbability())
                .isEqualTo(40);

        assertThat(state.owner())
                .isEqualTo("Alex Morgan");

        assertThat(state.value())
                .isEqualByComparingTo(new BigDecimal("3800000"));
    }

    @Test
    void reconstructsMarchState() {

        OpportunityAsOf state = historyService.findAsOf(
                "OPP-812",
                Instant.parse("2026-03-20T00:00:00Z"));

        assertThat(state.stage())
                .isEqualTo("Closed Won");

        assertThat(state.forecastProbability())
                .isEqualTo(100);

        assertThat(state.owner())
                .isEqualTo("Alex Morgan");

        assertThat(state.value())
                .isEqualByComparingTo(new BigDecimal("4000000"));
    }
}