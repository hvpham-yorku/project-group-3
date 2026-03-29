package com.yupathbuilder.backend.course_catalog.service;

import com.yupathbuilder.backend.course_catalog.dto.CourseDto;
import com.yupathbuilder.backend.course_catalog.store.CourseStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCatalogService {

    private final CourseStore courseStore;

    public CourseCatalogService(CourseStore courseStore) {
        this.courseStore = courseStore;
    }

    public List<CourseDto> listCourses(String q) {
        var courses = (q == null || q.isBlank())
            ? courseStore.listCourses()
            : courseStore.searchCourses(q);

        return courses.stream()
            .map(c -> new CourseDto(c.getCourseCode(), c.getTitle(), c.getDescription()))
            .toList();
    }

    public List<CourseDto> searchCourses(String q) {
        if (q == null || q.trim().isEmpty()) {
            return List.of();
        }

        return courseStore.searchCourses(q).stream()
            .map(c -> new CourseDto(c.getCourseCode(), c.getTitle(), c.getDescription()))
            .toList();
    }
}
