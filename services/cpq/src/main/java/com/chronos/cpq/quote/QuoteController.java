package com.chronos.cpq.quote;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quotes;

    public QuoteController(QuoteService quotes) {
        this.quotes = quotes;
    }

    @GetMapping("/{id}")
    public QuoteResponse findQuote(
            @PathVariable String id) {

        return quotes.findQuote(id);
    }
}