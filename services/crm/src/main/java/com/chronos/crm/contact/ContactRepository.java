package com.chronos.crm.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, String> {

    List<Contact> findByAccountId(String accountId);
}