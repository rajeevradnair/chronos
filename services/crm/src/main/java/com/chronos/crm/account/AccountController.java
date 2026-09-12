package com.chronos.crm.account;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository accounts;

    public AccountController(AccountRepository accounts) {
        this.accounts = accounts;
    }

    @GetMapping
    public List<Account> findAll() {
        return accounts.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable String id) {
        return accounts.findById(id).orElseThrow();
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return accounts.save(account);
    }
}