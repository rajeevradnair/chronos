package com.chronos.knowledge.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface KnowledgeDocumentVersionRepository
        extends JpaRepository<KnowledgeDocumentVersion, String> {

    @Query("""
        select v
        from KnowledgeDocumentVersion v
        where v.document.id = :documentId
          and v.validFrom <= :at
          and (v.validTo is null or :at < v.validTo)
        """)
    Optional<KnowledgeDocumentVersion> findEffectiveAt(
            @Param("documentId") String documentId,
            @Param("at") Instant at);
}