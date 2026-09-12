package com.chronos.crm.contact;

import com.chronos.crm.account.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    private String id;

    private String name;
    private String email;
    private String role;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    protected Contact() {
    }

    public Contact(
            String id,
            String name,
            String email,
            String role,
            Account account) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Account getAccount() {
        return account;
    }
}