package com.chronos.cpq.quote;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quotes;

    public QuoteController(QuoteService quotes) {
        this.quotes = quotes;
    }

    @GetMapping("/{id}/versions")
    public List<QuoteResponse> versions(
            @PathVariable String id) {

        return quotes.findVersions(id);
    }
}