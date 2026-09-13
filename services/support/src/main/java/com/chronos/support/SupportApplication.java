package com.chronos.support;

import com.chronos.support.ticket.Ticket;
import com.chronos.support.ticket.TicketEvent;
import com.chronos.support.ticket.TicketEventRepository;
import com.chronos.support.ticket.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@SpringBootApplication
public class SupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportApplication.class, args);
    }

    @Bean
    CommandLineRunner seedSupport(
            TicketRepository tickets,
            TicketEventRepository events) {

        return args -> {

            Ticket ticket =
                    tickets.findById("SUPPORT-91")
                            .orElseGet(() ->
                                    tickets.save(
                                            new Ticket(
                                                    "SUPPORT-91",
                                                    "ACC-1001",
                                                    "OPP-812",
                                                    "Requested conference-room capability unavailable",
                                                    "RESOLVED",
                                                    Instant.parse(
                                                            "2026-02-21T16:00:00Z"))));

            if (!events.existsById("SUPPORT-91-OPENED")) {
                events.save(
                        new TicketEvent(
                                "SUPPORT-91-OPENED",
                                ticket,
                                Instant.parse(
                                        "2026-02-21T16:00:00Z"),
                                "OPENED",
                                """
                                The requested conference-room capability
                                was confirmed as unavailable in the
                                current product offering.
                                """));
            }

            if (!events.existsById("SUPPORT-91-RESOLVED")) {
                events.save(
                        new TicketEvent(
                                "SUPPORT-91-RESOLVED",
                                ticket,
                                Instant.parse(
                                        "2026-07-10T16:00:00Z"),
                                "RESOLVED",
                                """
                                The previously unavailable capability
                                became available in the product.
                                """));
            }
        };
    }
}