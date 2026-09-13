package com.chronos.cpq.approval;

import com.chronos.cpq.quote.QuoteVersion;
import com.chronos.cpq.quote.QuoteVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class ApprovalService {

    private final PricingRuleRepository pricingRules;
    private final DiscountRequestRepository discountRequests;
    private final ApprovalRepository approvals;
    private final QuoteVersionRepository quoteVersions;

    public ApprovalService(
            PricingRuleRepository pricingRules,
            DiscountRequestRepository discountRequests,
            ApprovalRepository approvals,
            QuoteVersionRepository quoteVersions) {

        this.pricingRules = pricingRules;
        this.discountRequests = discountRequests;
        this.approvals = approvals;
        this.quoteVersions = quoteVersions;
    }

    @Transactional(readOnly = true)
public ApprovalPathResponse findApprovalPath(
        String quoteVersionId) {

    DiscountRequest request =
            discountRequests
                    .findByQuoteVersionId(quoteVersionId)
                    .orElseThrow();

    PricingRule rule = request.getPricingRule();

    boolean exceptionRequired =
            request.getRequestedPercent()
                    .compareTo(
                            rule.getApprovalThresholdPercent()) > 0;

    Approval approval =
            approvals
                    .findByDiscountRequestId(request.getId())
                    .orElse(null);

    return new ApprovalPathResponse(
            quoteVersionId,
            request.getId(),
            request.getRequestedPercent(),
            rule.getApprovalThresholdPercent(),
            exceptionRequired,
            rule.getRequiredApproverRole(),
            request.getReason(),
            request.getRequestedBy(),
            request.getRequestedAt(),

            approval == null ? null : approval.getId(),
            approval == null ? "PENDING" : approval.getStatus(),
            approval == null ? null : approval.getApprovedBy(),
            approval == null ? null : approval.getApproverRole(),
            approval == null ? null : approval.getApprovedAt(),
            approval == null ? null : approval.getApprovalSource());
}

    @Transactional
    public void createGlobalHotelsApprovalPathIfMissing() {

        PricingRule rule = pricingRules.findById("RULE-VP-15")
                .orElseGet(() ->
                        pricingRules.save(
                                new PricingRule(
                                        "RULE-VP-15",
                                        "VP approval required above 15%",
                                        new BigDecimal("15.00"),
                                        "VP")));

        QuoteVersion version = quoteVersions.findById("QUOTE-V3")
                .orElseThrow(() ->
                        new IllegalStateException(
                                "QUOTE-V3 must exist before creating approval path"));

        DiscountRequest request =
                discountRequests.findById("DISCOUNT-17")
                        .orElseGet(() ->
                                discountRequests.save(
                                        new DiscountRequest(
                                                "DISCOUNT-17",
                                                version,
                                                rule,
                                                new BigDecimal("17.00"),
                                                "Alex Morgan",
                                                Instant.parse("2026-02-28T12:00:00Z"),
                                                "Competitive pricing pressure")));

        if (!approvals.existsById("APPROVAL-88")) {

            approvals.save(
                    new Approval(
                            "APPROVAL-88",
                            request,
                            "APPROVED",
                            "Dana Brooks",
                            "VP",
                            Instant.parse("2026-03-05T15:00:00Z"),
                            "CPQ_EXCEPTION_WORKFLOW"));
        }
    }
}