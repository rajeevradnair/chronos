package com.chronos.cpq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:cpq-controller",
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
    void returnsGlobalHotelsQuoteVersions() throws Exception {

        mockMvc.perform(
                        get("/quotes/QUOTE-1001/versions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].quoteVersionId")
                        .value("QUOTE-V1"))
                .andExpect(jsonPath("$[0].versionNumber")
                        .value(1))
                .andExpect(jsonPath("$[1].quoteVersionId")
                        .value("QUOTE-V2"))
                .andExpect(jsonPath("$[1].versionNumber")
                        .value(2))
                .andExpect(jsonPath("$[2].quoteVersionId")
                        .value("QUOTE-V3"))
                .andExpect(jsonPath("$[2].versionNumber")
                        .value(3));
    }
}