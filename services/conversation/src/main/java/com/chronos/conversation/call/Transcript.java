package com.chronos.conversation.call;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "transcripts")
public class Transcript {

    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "call_id",
            nullable = false,
            unique = true)
    private Call call;

    private Instant createdAt;

    @Column(columnDefinition = "TEXT")
    private String text;

    protected Transcript() {
    }

    public Transcript(
            String id,
            Call call,
            Instant createdAt,
            String text) {

        this.id = id;
        this.call = call;
        this.createdAt = createdAt;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public Call getCall() {
        return call;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }
}