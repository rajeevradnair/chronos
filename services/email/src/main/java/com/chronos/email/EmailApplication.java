package com.chronos.email;

import com.chronos.email.message.EmailThread;
import com.chronos.email.message.EmailThreadRepository;
import com.chronos.email.message.Message;
import com.chronos.email.message.MessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@SpringBootApplication
public class EmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }

    @Bean
    CommandLineRunner seedEmail(
            EmailThreadRepository threads,
            MessageRepository messages) {

        return args -> {

            EmailThread thread =
                    threads.findById("THREAD-103")
                            .orElseGet(() ->
                                    threads.save(
                                            new EmailThread(
                                                    "THREAD-103",
                                                    "ACC-1001",
                                                    "OPP-812",
                                                    "GlobalHotels revised pricing")));

            if (!messages.existsById("EMAIL-103")) {
                messages.save(
                        new Message(
                                "EMAIL-103",
                                thread,
                                Instant.parse("2026-02-24T18:00:00Z"),
                                "maya.chen@globalhotels.example",
                                "alex.morgan@sony.example",
                                """
                                We need the revised commercial proposal
                                before our upcoming procurement decision.

                                Please provide your best revised pricing
                                by February 28 so that we can complete
                                our evaluation on schedule.
                                """));
            }
        };
    }
}