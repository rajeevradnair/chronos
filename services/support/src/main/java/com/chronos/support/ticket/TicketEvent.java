package com.chronos.support.ticket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "ticket_events")
public class TicketEvent {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    private Instant occurredAt;

    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String details;

    protected TicketEvent() {
    }

    public TicketEvent(
            String id,
            Ticket ticket,
            Instant occurredAt,
            String eventType,
            String details) {

        this.id = id;
        this.ticket = ticket;
        this.occurredAt = occurredAt;
        this.eventType = eventType;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDetails() {
        return details;
    }
}