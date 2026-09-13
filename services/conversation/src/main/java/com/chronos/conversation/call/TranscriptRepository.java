package com.chronos.conversation.call;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranscriptRepository
        extends JpaRepository<Transcript, String> {

    Optional<Transcript> findByCallId(String callId);
}