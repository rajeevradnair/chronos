package com.chronos.conversation;

import com.chronos.conversation.call.Call;
import com.chronos.conversation.call.CallRepository;
import com.chronos.conversation.call.Transcript;
import com.chronos.conversation.call.TranscriptRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@SpringBootApplication
public class ConversationApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                ConversationApplication.class,
                args);
    }

    @Bean
    CommandLineRunner seedConversation(
            CallRepository calls,
            TranscriptRepository transcripts) {

        return args -> {

            seedCall(
                    calls,
                    transcripts,
                    "CALL-20",
                    "TRANSCRIPT-20",
                    Instant.parse("2026-01-20T17:00:00Z"),
                    "GlobalHotels discovery",
                    "Maya Chen, Jordan Lee",
                    """
                    GlobalHotels plans to modernize approximately
                    300 conference rooms across 40 properties using
                    professional displays, PTZ cameras, projectors,
                    integration services, and enterprise support.
                    """);

            seedCall(
                    calls,
                    transcripts,
                    "CALL-44",
                    "TRANSCRIPT-44",
                    Instant.parse("2026-02-08T17:00:00Z"),
                    "GlobalHotels pricing discussion",
                    "Maya Chen, Alex Morgan",
                    """
                    Customer stated that competitor pricing was
                    materially lower and requested an additional
                    discount.
                    """);
        };
    }

    private void seedCall(
            CallRepository calls,
            TranscriptRepository transcripts,
            String callId,
            String transcriptId,
            Instant occurredAt,
            String title,
            String participants,
            String text) {

        if (calls.existsById(callId)) {
            return;
        }

        Call call = calls.save(
                new Call(
                        callId,
                        "ACC-1001",
                        "OPP-812",
                        occurredAt,
                        title,
                        participants));

        transcripts.save(
                new Transcript(
                        transcriptId,
                        call,
                        occurredAt.plusSeconds(3600),
                        text));
    }
}