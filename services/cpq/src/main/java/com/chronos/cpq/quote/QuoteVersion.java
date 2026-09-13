package com.chronos.cpq.quote;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quote_versions")
public class QuoteVersion {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    private int versionNumber;

    private Instant createdAt;

    @OneToMany(
            mappedBy = "quoteVersion",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<QuoteLine> lines = new ArrayList<>();

    protected QuoteVersion() {
    }

    public QuoteVersion(
            String id,
            Quote quote,
            int versionNumber,
            Instant createdAt) {

        this.id = id;
        this.quote = quote;
        this.versionNumber = versionNumber;
        this.createdAt = createdAt;
    }

    public void addLine(QuoteLine line) {
        lines.add(line);
    }

    public String getId() {
        return id;
    }

    public Quote getQuote() {
        return quote;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<QuoteLine> getLines() {
        return lines;
    }
}