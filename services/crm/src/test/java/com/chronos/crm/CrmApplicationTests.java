package com.chronos.crm;

import com.chronos.crm.account.AccountRepository;
import com.chronos.crm.contact.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:crm",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CrmApplicationTests {

    @Autowired
    AccountRepository accounts;

    @Autowired
    ContactRepository contacts;

    @Test
    void seedsGlobalHotels() {
        assertThat(accounts.findById("ACC-1001")).isPresent();

        assertThat(
                contacts.findByAccountId("ACC-1001")
        ).hasSize(1);
    }
}