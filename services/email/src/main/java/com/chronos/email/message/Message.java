package com.chronos.email.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "email_messages")
public class Message {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    private EmailThread thread;

    private Instant sentAt;

    private String sender;

    private String recipients;

    @Column(columnDefinition = "TEXT")
    private String body;

    protected Message() {
    }

    public Message(
            String id,
            EmailThread thread,
            Instant sentAt,
            String sender,
            String recipients,
            String body) {

        this.id = id;
        this.thread = thread;
        this.sentAt = sentAt;
        this.sender = sender;
        this.recipients = recipients;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public EmailThread getThread() {
        return thread;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipients() {
        return recipients;
    }

    public String getBody() {
        return body;
    }
}