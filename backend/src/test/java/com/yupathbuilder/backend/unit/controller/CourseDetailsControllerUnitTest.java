package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.course_catalog.controller.CourseDetailsController;
import com.yupathbuilder.backend.course_catalog.dto.CourseDetailsDto;
import com.yupathbuilder.backend.course_catalog.dto.MeetingDto;
import com.yupathbuilder.backend.course_catalog.dto.SectionInfoDto;
import com.yupathbuilder.backend.course_catalog.service.CourseDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseDetailsController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseDetailsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseDetailsService courseDetailsService;

    @Test
    void detailsReturnsCourseDetailsWhenFound() throws Exception {
        given(courseDetailsService.getDetails("EECS 2311", "FALL", 2026)).willReturn(
                new CourseDetailsDto(
                        "EECS 2311",
                        "Software Development Project",
                        "Project-based course",
                        "FALL 2026",
                        List.of(new SectionInfoDto("A", List.of(new MeetingDto("MON", "14:30", "15:30", "ACW 109"))))
                )
        );

        mockMvc.perform(get("/api/courses/EECS 2311/details").param("season", "FALL").param("year", "2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseCode").value("EECS 2311"))
                .andExpect(jsonPath("$.sections[0].sectionCode").value("A"));
    }

    @Test
    void detailsReturnsNotFoundWhenStoreReturnsNull() throws Exception {
        given(courseDetailsService.getDetails("NOPE 9999", "FALL", 2026)).willReturn(null);

        mockMvc.perform(get("/api/courses/NOPE 9999/details").param("season", "FALL").param("year", "2026"))
                .andExpect(status().isNotFound());
    }
}

