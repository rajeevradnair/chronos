package com.chronos.cpq.approval;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "approvals")
public class Approval {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "discount_request_id", nullable = false)
    private DiscountRequest discountRequest;

    private String status;

    private String approvedBy;

    private String approverRole;

    private Instant approvedAt;

    private String approvalSource;

    protected Approval() {
    }

    public Approval(
            String id,
            DiscountRequest discountRequest,
            String status,
            String approvedBy,
            String approverRole,
            Instant approvedAt,
            String approvalSource) {

        this.id = id;
        this.discountRequest = discountRequest;
        this.status = status;
        this.approvedBy = approvedBy;
        this.approverRole = approverRole;
        this.approvedAt = approvedAt;
        this.approvalSource = approvalSource;
    }

    public String getId() {
        return id;
    }

    public DiscountRequest getDiscountRequest() {
        return discountRequest;
    }

    public String getStatus() {
        return status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public String getApproverRole() {
        return approverRole;
    }

    public Instant getApprovedAt() {
        return approvedAt;
    }

    public String getApprovalSource() {
        return approvalSource;
    }
}