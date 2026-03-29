package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.course_catalog.controller.CourseControllerDb;
import com.yupathbuilder.backend.course_catalog.dto.CourseDto;
import com.yupathbuilder.backend.course_catalog.service.CourseCatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseControllerDb.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerDbUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseCatalogService courseCatalogService;

    @Test
    void listCoursesUsesListCoursesWhenQueryMissing() throws Exception {
        given(courseCatalogService.listCourses(null)).willReturn(List.of(
                new CourseDto("EECS 1011", "Computational Thinking Through Mechatronics", null)
        ));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("EECS 1011"));

        verify(courseCatalogService).listCourses(null);
    }

    @Test
    void listCoursesUsesSearchWhenQueryProvided() throws Exception {
        given(courseCatalogService.listCourses(eq("design"))).willReturn(List.of(
                new CourseDto("EECS 3311", "Software Design", null)
        ));

        mockMvc.perform(get("/api/courses").param("q", "design"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("EECS 3311"));

        verify(courseCatalogService).listCourses("design");
    }
}

