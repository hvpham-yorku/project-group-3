package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.controller.CourseControllerDb;
import com.yupathbuilder.backend.entity.CourseEntity;
import com.yupathbuilder.backend.store.CourseStore;
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
    private CourseStore courseStore;

    @Test
    void listCoursesUsesListCoursesWhenQueryMissing() throws Exception {
        CourseEntity course = new CourseEntity();
        course.setCourseCode("EECS 1011");
        course.setTitle("Computational Thinking Through Mechatronics");
        given(courseStore.listCourses()).willReturn(List.of(course));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("EECS 1011"));

        verify(courseStore).listCourses();
    }

    @Test
    void listCoursesUsesSearchWhenQueryProvided() throws Exception {
        CourseEntity course = new CourseEntity();
        course.setCourseCode("EECS 3311");
        course.setTitle("Software Design");
        given(courseStore.searchCourses(eq("design"))).willReturn(List.of(course));

        mockMvc.perform(get("/api/courses").param("q", "design"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("EECS 3311"));

        verify(courseStore).searchCourses("design");
    }
}
