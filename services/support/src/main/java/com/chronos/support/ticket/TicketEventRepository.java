package com.chronos.support.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketEventRepository
        extends JpaRepository<TicketEvent, String> {

    List<TicketEvent>
    findByTicketIdOrderByOccurredAt(String ticketId);
}