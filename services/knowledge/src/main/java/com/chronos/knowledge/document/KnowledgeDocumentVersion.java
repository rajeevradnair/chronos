package com.chronos.knowledge.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "knowledge_document_versions")
public class KnowledgeDocumentVersion {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private KnowledgeDocument document;

    private int version;

    private Instant validFrom;

    private Instant validTo;

    @Column(columnDefinition = "TEXT")
    private String content;

    protected KnowledgeDocumentVersion() {
    }

    public KnowledgeDocumentVersion(
            String id,
            KnowledgeDocument document,
            int version,
            Instant validFrom,
            Instant validTo,
            String content) {

        this.id = id;
        this.document = document;
        this.version = version;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public KnowledgeDocument getDocument() {
        return document;
    }

    public int getVersion() {
        return version;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public String getContent() {
        return content;
    }
}