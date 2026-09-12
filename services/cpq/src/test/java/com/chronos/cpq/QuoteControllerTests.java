package com.chronos.cpq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:cpq-api",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class QuoteControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void returnsGlobalHotelsQuoteV1() throws Exception {

        mockMvc.perform(
                        get("/quotes/QUOTE-V1"))

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$.quoteId")
                                .value("QUOTE-V1"))

                .andExpect(
                        jsonPath("$.accountId")
                                .value("ACC-1001"))

                .andExpect(
                        jsonPath("$.opportunityId")
                                .value("OPP-812"))

                .andExpect(
                        jsonPath("$.lines.length()")
                                .value(5))

                .andExpect(
                        jsonPath("$.total")
                                .value(3800000));
    }
}