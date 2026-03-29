package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.course_catalog.controller.SearchController;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
@AutoConfigureMockMvc(addFilters = false)
class SearchControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseCatalogService courseCatalogService;

    @Test
    void searchReturnsEmptyListWhenQueryBlank() throws Exception {
        mockMvc.perform(get("/api/search/courses").param("q", "   "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verifyNoInteractions(courseCatalogService);
    }

    @Test
    void searchReturnsMappedDtos() throws Exception {
        given(courseCatalogService.searchCourses(eq("software"))).willReturn(List.of(
                new CourseDto("EECS 2311", "Software Development Project", null)
        ));

        mockMvc.perform(get("/api/search/courses").param("q", "software"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("EECS 2311"))
                .andExpect(jsonPath("$[0].title").value("Software Development Project"));

        verify(courseCatalogService).searchCourses("software");
    }
}

