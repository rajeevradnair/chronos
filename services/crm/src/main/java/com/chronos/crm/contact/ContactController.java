package com.chronos.crm.contact;

import com.chronos.crm.account.Account;
import com.chronos.crm.account.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contacts;
    private final AccountRepository accounts;

    public ContactController(
            ContactRepository contacts,
            AccountRepository accounts) {

        this.contacts = contacts;
        this.accounts = accounts;
    }

    @GetMapping
    public List<Contact> findAll(
            @RequestParam(required = false) String accountId) {

        if (accountId != null) {
            return contacts.findByAccountId(accountId);
        }

        return contacts.findAll();
    }

    @PostMapping
    public Contact create(
            @RequestParam String accountId,
            @RequestBody Contact contact) {

        Account account = accounts.findById(accountId)
                .orElseThrow();

        return contacts.save(
                new Contact(
                        contact.getId(),
                        contact.getName(),
                        contact.getEmail(),
                        contact.getRole(),
                        account));
    }
}