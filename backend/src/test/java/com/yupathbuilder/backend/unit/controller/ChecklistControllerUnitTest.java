package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.repo.UserRepo;
import com.yupathbuilder.backend.checklist.dto.ChecklistResponseDto;
import com.yupathbuilder.backend.controller.ChecklistController;
import com.yupathbuilder.backend.store.CatalogStore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChecklistController.class)
@AutoConfigureMockMvc(addFilters = false)
class ChecklistControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepo repo;

    @MockitoBean
    private CatalogStore store;

    private static UsernamePasswordAuthenticationToken studentAuth() {
        return new UsernamePasswordAuthenticationToken(
                "wamiq@example.com",
                "n/a",
                List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))
        );
    }

    @Test
    void myChecklistReturnsUnauthorizedWhenAuthenticationMissing() throws Exception {

        mockMvc.perform(get("/api/me/checklist"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void myChecklistReturnsBadRequestWhenUserHasNoProgram() throws Exception {

        UserEntity user = new UserEntity(
                "wamiq@example.com",
                "hash",
                null
        );

        given(repo.findByEmail("wamiq@example.com"))
                .willReturn(Optional.of(user));

        mockMvc.perform(get("/api/me/checklist")
                        .principal(studentAuth()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void myChecklistReturnsChecklistForAuthenticatedUser() throws Exception {

        UserEntity user = new UserEntity(
                "wamiq@example.com",
                "hash",
                1L
        );

        given(repo.findByEmail("wamiq@example.com"))
                .willReturn(Optional.of(user));

        given(store.checklistByProgramId(1L))
                .willReturn(new ChecklistResponseDto(1L, List.of()));

        mockMvc.perform(get("/api/me/checklist")
                        .principal(studentAuth()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.programId").value(1));
    }
}
