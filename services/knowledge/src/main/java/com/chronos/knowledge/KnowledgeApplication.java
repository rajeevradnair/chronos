package com.chronos.knowledge;

import com.chronos.knowledge.policy.PricingPolicy;
import com.chronos.knowledge.policy.PricingPolicyRepository;
import com.chronos.knowledge.policy.PricingPolicyVersion;
import com.chronos.knowledge.policy.PricingPolicyVersionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

import com.chronos.knowledge.document.KnowledgeDocument;
import com.chronos.knowledge.document.KnowledgeDocumentRepository;
import com.chronos.knowledge.document.KnowledgeDocumentType;
import com.chronos.knowledge.document.KnowledgeDocumentVersion;
import com.chronos.knowledge.document.KnowledgeDocumentVersionRepository;

@SpringBootApplication
public class KnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeApplication.class, args);
    }

    @Bean
    CommandLineRunner seedPricingPolicy(
            PricingPolicyRepository policies,
            PricingPolicyVersionRepository versions) {

        return args -> {

            PricingPolicy policy =
                    policies.findById("POLICY-DISCOUNT")
                            .orElseGet(() ->
                                    policies.save(
                                            new PricingPolicy(
                                                    "POLICY-DISCOUNT",
                                                    "Enterprise Discount Policy")));

            if (!versions.existsById("POLICY-V1")) {
                versions.save(
                        new PricingPolicyVersion(
                                "POLICY-V1",
                                policy,
                                1,
                                Instant.parse("2026-01-01T00:00:00Z"),
                                Instant.parse("2026-02-12T00:00:00Z"),
                                """
                                Standard sales discounts are governed
                                by the enterprise pricing approval process.
                                """));
            }

            if (!versions.existsById("POLICY-V2")) {
                versions.save(
                        new PricingPolicyVersion(
                                "POLICY-V2",
                                policy,
                                2,
                                Instant.parse("2026-02-12T00:00:00Z"),
                                null,
                                """
                                Discounts above 15% require
                                VP authorization.
                                """));
            }
        };
    }

    @Bean
    CommandLineRunner seedKnowledgeDocuments(
            KnowledgeDocumentRepository documents,
            KnowledgeDocumentVersionRepository versions) {

        return args -> {

            seedProductDocumentation(documents, versions);
            seedBattlecard(documents, versions);
            seedPlaybook(documents, versions);
        };
    }

    private void seedProductDocumentation(
            KnowledgeDocumentRepository documents,
            KnowledgeDocumentVersionRepository versions) {

        KnowledgeDocument document =
                documents.findById("DOC-PRODUCT-CAPABILITY")
                        .orElseGet(() ->
                                documents.save(
                                        new KnowledgeDocument(
                                                "DOC-PRODUCT-CAPABILITY",
                                                KnowledgeDocumentType.PRODUCT_DOCUMENT,
                                                "Conference Room Product Capability")));

        if (!versions.existsById("DOC-PRODUCT-CAPABILITY-V1")) {
            versions.save(
                    new KnowledgeDocumentVersion(
                            "DOC-PRODUCT-CAPABILITY-V1",
                            document,
                            1,
                            Instant.parse("2026-02-21T00:00:00Z"),
                            Instant.parse("2026-07-10T00:00:00Z"),
                            """
                            The requested capability is not currently
                            supported by the product.
                            """));
        }

        if (!versions.existsById("DOC-PRODUCT-CAPABILITY-V2")) {
            versions.save(
                    new KnowledgeDocumentVersion(
                            "DOC-PRODUCT-CAPABILITY-V2",
                            document,
                            2,
                            Instant.parse("2026-07-10T00:00:00Z"),
                            null,
                            """
                            The previously unavailable capability
                            is now supported.
                            """));
        }
    }

    private void seedBattlecard(
            KnowledgeDocumentRepository documents,
            KnowledgeDocumentVersionRepository versions) {

        KnowledgeDocument document =
                documents.findById("BATTLECARD-COMPETITIVE")
                        .orElseGet(() ->
                                documents.save(
                                        new KnowledgeDocument(
                                                "BATTLECARD-COMPETITIVE",
                                                KnowledgeDocumentType.BATTLECARD,
                                                "Competitive Pricing Battlecard")));

        if (!versions.existsById("BATTLECARD-COMPETITIVE-V1")) {
            versions.save(
                    new KnowledgeDocumentVersion(
                            "BATTLECARD-COMPETITIVE-V1",
                            document,
                            1,
                            Instant.parse("2026-02-02T00:00:00Z"),
                            null,
                            """
                            When materially lower competitor pricing
                            threatens a strategic opportunity, document
                            the competitive evidence before requesting
                            exceptional pricing.
                            """));
        }
    }

    private void seedPlaybook(
            KnowledgeDocumentRepository documents,
            KnowledgeDocumentVersionRepository versions) {

        KnowledgeDocument document =
                documents.findById("PLAYBOOK-STRATEGIC-DEAL")
                        .orElseGet(() ->
                                documents.save(
                                        new KnowledgeDocument(
                                                "PLAYBOOK-STRATEGIC-DEAL",
                                                KnowledgeDocumentType.PLAYBOOK,
                                                "Strategic Deal Playbook")));

        if (!versions.existsById("PLAYBOOK-STRATEGIC-DEAL-V1")) {
            versions.save(
                    new KnowledgeDocumentVersion(
                            "PLAYBOOK-STRATEGIC-DEAL-V1",
                            document,
                            1,
                            Instant.parse("2026-01-01T00:00:00Z"),
                            null,
                            """
                            Strategic opportunities should preserve
                            customer requirements, competitive context,
                            pricing rationale, and approval provenance.
                            """));
        }
    }

}