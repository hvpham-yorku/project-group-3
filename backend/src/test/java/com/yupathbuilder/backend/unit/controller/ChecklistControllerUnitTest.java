package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.auth.AppUser;
import com.yupathbuilder.backend.auth.UserService;
import com.yupathbuilder.backend.checklist.dto.ChecklistResponseDto;
import com.yupathbuilder.backend.controller.ChecklistController;
import com.yupathbuilder.backend.store.CatalogStore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChecklistController.class)
@AutoConfigureMockMvc(addFilters = false)
class ChecklistControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CatalogStore store;

    @Test
    void myChecklistReturnsUnauthorizedWhenAuthenticationMissing() throws Exception {
        mockMvc.perform(get("/api/me/checklist"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Disabled("Temporarily disabled due to Boot 4 auth context issue in WebMvcTest")
    @WithMockUser(username = "wamiq@example.com", roles = "STUDENT")
    void myChecklistReturnsBadRequestWhenUserHasNoProgram() throws Exception {
        given(userService.find("wamiq@example.com")).willReturn(Optional.of(
                new AppUser("wamiq@example.com", "hash", Set.of("ROLE_STUDENT"), "Wamiq", "Lakha", null)
        ));

        mockMvc.perform(get("/api/me/checklist"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Disabled("Temporarily disabled due to Boot 4 auth context issue in WebMvcTest")
    @WithMockUser(username = "wamiq@example.com", roles = "STUDENT")
    void myChecklistReturnsChecklistForAuthenticatedUser() throws Exception {
        given(userService.find("wamiq@example.com")).willReturn(Optional.of(
                new AppUser("wamiq@example.com", "hash", Set.of("ROLE_STUDENT"), "Wamiq", "Lakha", 1L)
        ));
        given(store.checklistByProgramId(1L)).willReturn(new ChecklistResponseDto(1L, List.of()));

        mockMvc.perform(get("/api/me/checklist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.programId").value(1));
    }
}