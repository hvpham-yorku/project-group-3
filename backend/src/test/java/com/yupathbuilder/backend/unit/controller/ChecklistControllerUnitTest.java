package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.program_system.dto.ChecklistResponseDto;
import com.yupathbuilder.backend.program_system.controller.ChecklistController;
import com.yupathbuilder.backend.program_system.service.UserChecklistService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChecklistController.class)
@AutoConfigureMockMvc(addFilters = false)
class ChecklistControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserChecklistService userChecklistService;

    private static UsernamePasswordAuthenticationToken studentAuth() {
        return new UsernamePasswordAuthenticationToken(
                "wamiq@example.com",
                "n/a",
                List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))
        );
    }

    @Test
    void myChecklistReturnsUnauthorizedWhenAuthenticationMissing() throws Exception {
        doReturn(org.springframework.http.ResponseEntity.status(401).body("Unauthorized"))
                .when(userChecklistService).getChecklistFor(null);

        mockMvc.perform(get("/api/me/checklist"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void myChecklistReturnsBadRequestWhenUserHasNoProgram() throws Exception {
        doReturn(org.springframework.http.ResponseEntity.badRequest().body("User has no program selected"))
                .when(userChecklistService).getChecklistFor(org.mockito.ArgumentMatchers.any());

        mockMvc.perform(get("/api/me/checklist")
                        .principal(studentAuth()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void myChecklistReturnsChecklistForAuthenticatedUser() throws Exception {
        doReturn(org.springframework.http.ResponseEntity.ok(new ChecklistResponseDto(1L, List.of())))
                .when(userChecklistService).getChecklistFor(org.mockito.ArgumentMatchers.any());

        mockMvc.perform(get("/api/me/checklist")
                        .principal(studentAuth()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.programId").value(1));
    }
}

