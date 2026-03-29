package com.yupathbuilder.backend.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.scheduler_system.controller.SavedCourseSelectionController;
import com.yupathbuilder.backend.scheduler_system.dto.SaveCourseSelectionRequest;
import com.yupathbuilder.backend.scheduler_system.dto.SavedCourseSelectionDto;
import com.yupathbuilder.backend.scheduler_system.service.SavedCourseSelectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SavedCourseSelectionController.class)
@AutoConfigureMockMvc(addFilters = false)
class SavedCourseSelectionControllerUnitTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SavedCourseSelectionService savedCourseSelectionService;

    @Test
    void listReturnsSavedSelections() throws Exception {
        given(savedCourseSelectionService.listSelections("wamiq@example.com"))
                .willReturn(List.of(new SavedCourseSelectionDto("FALL 2026", "EECS 1011")));

        mockMvc.perform(get("/api/me/selected-courses").principal(auth()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].term").value("FALL 2026"))
                .andExpect(jsonPath("$[0].courseCode").value("EECS 1011"));
    }

    @Test
    void saveReturnsCreatedSelection() throws Exception {
        SaveCourseSelectionRequest request = new SaveCourseSelectionRequest("FALL 2026", "EECS 1011");

        given(savedCourseSelectionService.saveSelection("wamiq@example.com", "FALL 2026", "EECS 1011"))
                .willReturn(new SavedCourseSelectionDto("FALL 2026", "EECS 1011"));

        mockMvc.perform(post("/api/me/selected-courses")
                        .principal(auth())
                        .contentType(APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.term").value("FALL 2026"))
                .andExpect(jsonPath("$.courseCode").value("EECS 1011"));
    }

    @Test
    void removeReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/me/selected-courses")
                        .principal(auth())
                        .queryParam("term", "FALL 2026")
                        .queryParam("courseCode", "EECS 1011"))
                .andExpect(status().isNoContent());

        verify(savedCourseSelectionService).removeSelection("wamiq@example.com", "FALL 2026", "EECS 1011");
    }

    private static UsernamePasswordAuthenticationToken auth() {
        return new UsernamePasswordAuthenticationToken(
                "wamiq@example.com",
                "token",
                AuthorityUtils.NO_AUTHORITIES
        );
    }
}

