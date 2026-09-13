package com.chronos.email;

import com.chronos.email.message.EmailThreadRepository;
import com.chronos.email.message.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:email",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class EmailApplicationTests {

    @Autowired
    EmailThreadRepository threads;

    @Autowired
    MessageRepository messages;

    @Test
    void globalHotelsThreadBelongsToOpportunity() {

        var thread =
                threads.findById("THREAD-103")
                        .orElseThrow();

        assertThat(thread.getAccountId())
                .isEqualTo("ACC-1001");

        assertThat(thread.getOpportunityId())
                .isEqualTo("OPP-812");
    }

    @Test
    void procurementEmailPreservesPricingDeadlineEvidence() {

        var history =
                messages.findByThreadIdOrderBySentAt(
                        "THREAD-103");

        assertThat(history)
                .hasSize(1);

        var email = history.get(0);

        assertThat(email.getId())
                .isEqualTo("EMAIL-103");

        assertThat(email.getBody())
                .contains("revised pricing")
                .contains("February 28")
                .contains("procurement decision");
    }
}