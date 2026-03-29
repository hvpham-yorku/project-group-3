package com.yupathbuilder.backend.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.authentication.AuthController;
import com.yupathbuilder.backend.authentication.AuthService;
import com.yupathbuilder.backend.authentication.UserProfileService;
import com.yupathbuilder.backend.authentication.dto.AuthResponse;
import com.yupathbuilder.backend.authentication.dto.LoginRequest;
import com.yupathbuilder.backend.authentication.dto.RegisterRequest;
import com.yupathbuilder.backend.global_exception_handler.InvalidCredentialsException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerUnitTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UserProfileService userProfileService;

    @Test
    void registerReturnsTokenOnSuccess() throws Exception {

        var request = new RegisterRequest(
                "Wamiq",
                "Lakha",
                "wamiq@example.com",
                1L,
                "password123",
                "password123"
        );

        given(authService.register(request))
                .willReturn(new AuthResponse("token123", "wamiq@example.com"));

        mockMvc.perform(post("/api/authentication/register")
                        .contentType(APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.username").value("wamiq@example.com"));
    }

    @Test
    void loginReturnsTokenForValidCredentials() throws Exception {

        var request = new LoginRequest(
                "wamiq@example.com",
                "password123"
        );

        given(authService.login(request))
                .willReturn(new AuthResponse("token123", "wamiq@example.com"));

        mockMvc.perform(post("/api/authentication/login")
                        .contentType(APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.username").value("wamiq@example.com"));
    }

    @Test
    void loginReturnsBadRequestForInvalidInput() throws Exception {

        String body = """
                {
                  "email": "",
                  "password": ""
                }
                """;

        mockMvc.perform(post("/api/authentication/login")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginReturnsUnauthorizedForInvalidCredentials() throws Exception {

        var request = new LoginRequest(
                "wamiq@example.com",
                "wrong-password"
        );

        given(authService.login(request))
                .willThrow(new InvalidCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/api/authentication/login")
                        .contentType(APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

    @Test
    void meReturnsOk() throws Exception {

        mockMvc.perform(get("/api/authentication/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("Authenticated"));
    }
}
