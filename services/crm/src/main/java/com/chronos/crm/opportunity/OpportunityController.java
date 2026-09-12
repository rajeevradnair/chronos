package com.chronos.crm.opportunity;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/opportunities")
public class OpportunityController {

    private final OpportunityHistoryService history;

    public OpportunityController(
            OpportunityHistoryService history) {

        this.history = history;
    }

    @GetMapping("/{id}/as-of")
    public OpportunityAsOf findAsOf(
            @PathVariable String id,
            @RequestParam Instant at) {

        return history.findAsOf(id, at);
    }
}