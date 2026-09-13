package com.chronos.conversation;

import com.chronos.conversation.call.CallRepository;
import com.chronos.conversation.call.TranscriptRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:conversation",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ConversationApplicationTests {

    @Autowired
    CallRepository calls;

    @Autowired
    TranscriptRepository transcripts;

    @Test
    void seedsGlobalHotelsConversationHistory() {

        var history =
                calls.findByOpportunityIdOrderByOccurredAt(
                        "OPP-812");

        assertThat(history)
                .hasSize(2);

        assertThat(history.get(0).getId())
                .isEqualTo("CALL-20");

        assertThat(history.get(1).getId())
                .isEqualTo("CALL-44");
    }

    @Test
    void preservesCompetitivePricingEvidence() {

        var transcript =
                transcripts.findByCallId("CALL-44")
                        .orElseThrow();

        assertThat(transcript.getText())
                .contains("competitor pricing");

        String normalized =
                transcript.getText()
                        .replaceAll("\\s+", " ");

        assertThat(normalized)
                .contains("additional discount");
    }
}