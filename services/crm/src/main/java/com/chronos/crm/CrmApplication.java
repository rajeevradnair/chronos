package com.chronos.crm;

import com.chronos.crm.account.Account;
import com.chronos.crm.account.AccountRepository;
import com.chronos.crm.contact.Contact;
import com.chronos.crm.contact.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class, args);
    }

    @Bean
    CommandLineRunner seedGlobalHotels(
            AccountRepository accounts,
            ContactRepository contacts) {

        return args -> {
            if (accounts.existsById("ACC-1001")) {
                return;
            }

            Account globalHotels = accounts.save(
                    new Account(
                            "ACC-1001",
                            "GlobalHotels Corp",
                            "Hospitality"));

            contacts.save(
                    new Contact(
                            "CON-1001",
                            "Maya Chen",
                            "maya.chen@globalhotels.example",
                            "Procurement Director",
                            globalHotels));
        };
    }
}