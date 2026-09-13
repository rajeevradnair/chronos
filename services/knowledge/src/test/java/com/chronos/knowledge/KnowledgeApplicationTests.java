package com.chronos.knowledge;

import com.chronos.knowledge.policy.PricingPolicyVersionRepository;
import com.chronos.knowledge.document.KnowledgeDocumentVersionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:knowledge",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class KnowledgeApplicationTests {

    @Autowired
    PricingPolicyVersionRepository versions;

    @Autowired
    KnowledgeDocumentVersionRepository documentVersions;

    @Test
    void returnsPolicyVersionEffectiveBeforeChange() {

        var policy =
                versions.findEffectiveAt(
                        "POLICY-DISCOUNT",
                        Instant.parse("2026-02-08T17:00:00Z"))
                        .orElseThrow();

        assertThat(policy.getId())
                .isEqualTo("POLICY-V1");
    }

    @Test
    void returnsPolicyVersionEffectiveAfterChange() {

        var policy =
                versions.findEffectiveAt(
                        "POLICY-DISCOUNT",
                        Instant.parse("2026-02-28T12:00:00Z"))
                        .orElseThrow();

        assertThat(policy.getId())
                .isEqualTo("POLICY-V2");

        assertThat(policy.getContent())
                .contains("above 15%")
                .contains("VP authorization");
    }


    @Test
    void historicalProductDocumentationDoesNotSeeFutureCapability() {

        var version =
                documentVersions.findEffectiveAt(
                        "DOC-PRODUCT-CAPABILITY",
                        Instant.parse("2026-02-28T12:00:00Z"))
                        .orElseThrow();

        assertThat(version.getId())
                .isEqualTo("DOC-PRODUCT-CAPABILITY-V1");

    }

    @Test
    void currentProductDocumentationSeesLaterCapability() {

        var version =
                documentVersions.findEffectiveAt(
                        "DOC-PRODUCT-CAPABILITY",
                        Instant.parse("2026-08-01T12:00:00Z"))
                        .orElseThrow();

        assertThat(version.getId())
                .isEqualTo("DOC-PRODUCT-CAPABILITY-V2");

        assertThat(version.getContent())
                .contains("now supported");
    }

    @Test
    void battlecardAndPlaybookArePersisted() {

        assertThat(
                documentVersions.findEffectiveAt(
                        "BATTLECARD-COMPETITIVE",
                        Instant.parse("2026-02-28T12:00:00Z")))
                .isPresent();

        assertThat(
                documentVersions.findEffectiveAt(
                        "PLAYBOOK-STRATEGIC-DEAL",
                        Instant.parse("2026-02-28T12:00:00Z")))
                .isPresent();
    }

}