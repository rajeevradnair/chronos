package com.chronos.cpq.approval;

import java.math.BigDecimal;
import java.time.Instant;

public record ApprovalPathResponse(
        String quoteVersionId,
        String discountRequestId,
        BigDecimal requestedPercent,
        BigDecimal approvalThresholdPercent,
        boolean exceptionRequired,
        String requiredApproverRole,
        String requestReason,
        String requestedBy,
        Instant requestedAt,
        String approvalId,
        String approvalStatus,
        String approvedBy,
        String approverRole,
        Instant approvedAt,
        String approvalSource) {
}