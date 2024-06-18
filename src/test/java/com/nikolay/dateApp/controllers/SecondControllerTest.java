package com.nikolay.dateApp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.services.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SecondControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;
    @Autowired
    private Person person;
    //private Worksheet worksheet;
    private ObjectMapper objectMapper;

    @Test
    void secondPage() {
    }

    @Test
    void form() {
    }

    @Test
    void getForms() {
    }

    @Test
    void deleteById() {
    }
}