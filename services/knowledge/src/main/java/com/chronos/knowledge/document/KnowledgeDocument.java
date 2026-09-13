package com.chronos.knowledge.document;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "knowledge_documents")
public class KnowledgeDocument {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private KnowledgeDocumentType type;

    private String name;

    protected KnowledgeDocument() {
    }

    public KnowledgeDocument(
            String id,
            KnowledgeDocumentType type,
            String name) {

        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public KnowledgeDocumentType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}