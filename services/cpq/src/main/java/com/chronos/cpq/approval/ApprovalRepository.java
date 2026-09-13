package com.chronos.cpq.approval;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalRepository
        extends JpaRepository<Approval, String> {

    Optional<Approval> findByDiscountRequestId(
            String discountRequestId);
}