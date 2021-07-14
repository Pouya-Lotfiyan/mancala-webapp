package com.pouya.mancalaapi.unit.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest
public class BaseWebTest {

    protected   static ObjectMapper objectMapper;

    @Autowired
     protected MockMvc mockMvc;

    @BeforeAll
    static void setUp() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

}
