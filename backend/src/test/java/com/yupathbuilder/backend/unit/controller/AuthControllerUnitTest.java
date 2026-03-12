/*package com.yupathbuilder.backend.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.authentication.AppUser;
import com.yupathbuilder.backend.auth.UserService;
import com.yupathbuilder.backend.auth.dto.RegisterRequest;
import com.yupathbuilder.backend.auth.jwt.JwtUtil;
import com.yupathbuilder.backend.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @MockitoBean
    private UserService users;

    @MockitoBean
    private PasswordEncoder encoder;

    @MockitoBean
    private JwtUtil jwt;

    @Test
    void registerReturnsBadRequestWhenPasswordsDoNotMatch() throws Exception {
        var request = new RegisterRequest(
                "Wamiq",
                "Lakha",
                "wamiq@example.com",
                1L,
                "password123",
                "different123"
        );

        mockMvc.perform(post("/api/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

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

        var user = new AppUser(
                "wamiq@example.com",
                "hashed",
                Set.of("ROLE_STUDENT"),
                "Wamiq",
                "Lakha",
                1L
        );

        given(users.register(anyString(), anyString(), anyString(), anyString(), anyLong()))
                .willReturn(user);
        given(jwt.generateToken("wamiq@example.com", List.of("ROLE_STUDENT")))
                .willReturn("token123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.username").value("wamiq@example.com"));
    }

    @Test
    void loginReturnsTokenForValidCredentials() throws Exception {
        String body = """
                {
                  "username": "wamiq@example.com",
                  "password": "password123"
                }
                """;

        var user = new AppUser(
                "wamiq@example.com",
                "hashed",
                Set.of("ROLE_STUDENT"),
                "Wamiq",
                "Lakha",
                1L
        );

        given(users.find("wamiq@example.com")).willReturn(Optional.of(user));
        given(encoder.matches("password123", "hashed")).willReturn(true);
        given(jwt.generateToken("wamiq@example.com", List.of("ROLE_STUDENT")))
                .willReturn("token123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.username").value("wamiq@example.com"));
    }

    @Test
    void loginReturnsUnauthorizedForUnknownUser() throws Exception {
        String body = """
                {
                  "username": "missing@example.com",
                  "password": "password123"
                }
                """;

        given(users.find("missing@example.com")).willReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void meReturnsOk() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk());
    }
}*/