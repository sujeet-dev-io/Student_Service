package com.studentapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecuredControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Value("${api.key}")
    String apiKey;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                //.apply(springSecurity())
                .build();
    }

    /**
     * Test cases to validate the Authorized APIs with positive and negative scenario's.
     *
     */

    @WithMockUser(authorities = "SUPER_ADMIN")
    @Test
    public void givenPrivateHelloRequestWithCorrectAuthority_shouldSucceedWith200() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mockMvc.perform(get("/api/private/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("This is secured hello method"));
    }

    @Test
    @WithMockUser(authorities = "WRONG_USER")
    public void givenPrivateHelloRequestWithWrongAuthority_shouldFailWith403() throws Exception {
        mockMvc.perform(get("/api/private/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @WithMockUser(authorities = "SUPER_ADMIN")
    @Test
    public void givenPrivateDeleteRequestWithCorrectAuthority_shouldSucceedWith200() throws Exception {
        mockMvc.perform(delete("/api/private/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("This is secured delete method"));
    }

    @WithMockUser(authorities = "WRONG_USER")
    @Test
    public void givenPrivateDeleteRequestWithCorrectAuthority_shouldFailWith403() throws Exception {
        mockMvc.perform(delete("/api/private/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
