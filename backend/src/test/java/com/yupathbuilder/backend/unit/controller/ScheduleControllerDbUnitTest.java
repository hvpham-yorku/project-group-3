package com.yupathbuilder.backend.unit.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.scheduler_system.controller.ScheduleControllerDb;
import com.yupathbuilder.backend.scheduler_system.dto.ChosenSectionDto;
import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.scheduler_system.service.ScheduleBuildService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleControllerDb.class)
@AutoConfigureMockMvc(addFilters = false)
class ScheduleControllerDbUnitTest {

    @Autowired
    private MockMvc mockMvc;

    //private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @MockitoBean
    private ScheduleBuildService scheduleBuildService;

    @Test
    void buildMapsRequestBodyAndReturnsStoreResponse() throws Exception {
        var response = new ScheduleBuildResponse(
                "FALL 2026",
                List.of(new ChosenSectionDto(
                        "EECS 1011",
                        "A",
                        "MON,WED",
                        "09:30",
                        "10:30",
                        "LAS 1004"
                ))
        );

        given(scheduleBuildService.build(eq("FALL 2026"), eq(List.of("EECS 1011", "MATH 1013"))))
                .willReturn(response);

        String body = """
                {
                  "term": "FALL 2026",
                  "courseCodes": ["EECS 1011", "MATH 1013"]
                }
                """;

        mockMvc.perform(post("/api/schedule/build")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.term").value("FALL 2026"))
                .andExpect(jsonPath("$.chosenSections[0].courseCode").value("EECS 1011"))
                .andExpect(jsonPath("$.chosenSections[0].sectionId").value("A"));
    }

    @Test
    void buildReturnsBadRequestWithMessageWhenStoreRejectsRequest() throws Exception {
        given(scheduleBuildService.build(eq("SUMMER 2027"), eq(List.of("EECS 1011"))))
                .willThrow(new IllegalArgumentException("No sections for EECS 1011 in SUMMER 2027"));

        String body = """
                {
                  "term": "SUMMER 2027",
                  "courseCodes": ["EECS 1011"]
                }
                """;

        mockMvc.perform(post("/api/schedule/build")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No sections for EECS 1011 in SUMMER 2027"));
    }
}

