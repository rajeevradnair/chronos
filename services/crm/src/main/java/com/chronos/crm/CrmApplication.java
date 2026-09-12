package com.chronos.crm;

import com.chronos.crm.account.Account;
import com.chronos.crm.account.AccountRepository;
import com.chronos.crm.contact.Contact;
import com.chronos.crm.contact.ContactRepository;
import com.chronos.crm.opportunity.*;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class, args);
    }

    @Bean
    CommandLineRunner seedGlobalHotels(
            AccountRepository accounts,
            ContactRepository contacts,
            OpportunityRepository opportunities,
            StageHistoryRepository stageHistory,
            ForecastHistoryRepository forecastHistory,
            OwnerHistoryRepository ownerHistory,
            ValueHistoryRepository valueHistory) {

        return args -> {

            Account globalHotels = accounts.findById("ACC-1001")
                    .orElseGet(() -> accounts.save(
                            new Account(
                                    "ACC-1001",
                                    "GlobalHotels Corp",
                                    "Hospitality")));

            if (!contacts.existsById("CON-1001")) {
                contacts.save(
                        new Contact(
                                "CON-1001",
                                "Maya Chen",
                                "maya.chen@globalhotels.example",
                                "Procurement Director",
                                globalHotels));
            }

            if (!opportunities.existsById("OPP-812")) {

                opportunities.save(
                        new Opportunity(
                                "OPP-812",
                                "GlobalHotels Modernization",
                                new BigDecimal("3800000"),
                                "Closed Won",
                                100,
                                "Alex Morgan",
                                globalHotels));

                stageHistory.save(
                        new StageHistory(
                                "STAGE-1",
                                "OPP-812",
                                "Prospecting",
                                Instant.parse("2026-01-12T00:00:00Z")));

                stageHistory.save(
                        new StageHistory(
                                "STAGE-2",
                                "OPP-812",
                                "Qualification",
                                Instant.parse("2026-02-02T00:00:00Z")));

                stageHistory.save(
                        new StageHistory(
                                "STAGE-3",
                                "OPP-812",
                                "Closed Won",
                                Instant.parse("2026-03-18T00:00:00Z")));

                forecastHistory.save(
                        new ForecastHistory(
                                "FORECAST-1",
                                "OPP-812",
                                40,
                                Instant.parse("2026-01-12T00:00:00Z")));

                forecastHistory.save(
                        new ForecastHistory(
                                "FORECAST-2",
                                "OPP-812",
                                80,
                                Instant.parse("2026-02-28T00:00:00Z")));

                forecastHistory.save(
                        new ForecastHistory(
                                "FORECAST-3",
                                "OPP-812",
                                100,
                                Instant.parse("2026-03-18T00:00:00Z")));

                ownerHistory.save(
                        new OwnerHistory(
                                "OWNER-1",
                                "OPP-812",
                                "Jordan Lee",
                                Instant.parse("2026-01-12T00:00:00Z")));

                ownerHistory.save(
                        new OwnerHistory(
                                "OWNER-2",
                                "OPP-812",
                                "Alex Morgan",
                                Instant.parse("2026-02-15T00:00:00Z")));

                valueHistory.save(
                        new ValueHistory(
                                "VALUE-1",
                                "OPP-812",
                                new BigDecimal("3800000"),
                                Instant.parse("2026-01-12T00:00:00Z")));

                valueHistory.save(
                        new ValueHistory(
                                "VALUE-2",
                                "OPP-812",
                                new BigDecimal("4000000"),
                                Instant.parse("2026-02-28T00:00:00Z")));
            }
        };
    }
}