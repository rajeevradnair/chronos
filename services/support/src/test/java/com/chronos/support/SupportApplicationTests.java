package com.chronos.support;

import com.chronos.support.ticket.TicketEventRepository;
import com.chronos.support.ticket.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:support",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class SupportApplicationTests {

    @Autowired
    TicketRepository tickets;

    @Autowired
    TicketEventRepository events;

    @Test
    void globalHotelsSupportTicketExists() {

        var ticket =
                tickets.findById("SUPPORT-91")
                        .orElseThrow();

        assertThat(ticket.getAccountId())
                .isEqualTo("ACC-1001");

        assertThat(ticket.getOpportunityId())
                .isEqualTo("OPP-812");
    }

    @Test
    void supportHistoryPreservesTechnicalTimeline() {

        var history =
                events.findByTicketIdOrderByOccurredAt(
                        "SUPPORT-91");

        assertThat(history)
                .hasSize(2);

        assertThat(history.get(0).getEventType())
                .isEqualTo("OPENED");

        assertThat(history.get(0).getDetails())
                .contains("unavailable");

        assertThat(history.get(1).getEventType())
                .isEqualTo("RESOLVED");

        assertThat(history.get(1).getDetails())
                .contains("became available");
    }
}