package com.chronos.cpq.approval;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quote-versions")
public class ApprovalController {

    private final ApprovalService approvals;

    public ApprovalController(
            ApprovalService approvals) {

        this.approvals = approvals;
    }

    @GetMapping("/{id}/approval-path")
    public ApprovalPathResponse approvalPath(
            @PathVariable String id) {

        return approvals.findApprovalPath(id);
    }
}