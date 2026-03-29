package com.yupathbuilder.backend.course_catalog.store.stub;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.course_catalog.entity.CourseEntity;
import com.yupathbuilder.backend.course_catalog.store.CourseStore;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

@Component
@ConditionalOnProperty(name = "app.store", havingValue = "stub")
public class StubCourseStore implements CourseStore {

    private final List<CourseEntity> courses;

    public StubCourseStore(ObjectMapper mapper) {
        this.courses = Collections.unmodifiableList(loadCourses(mapper));
    }

    @Override
    public List<CourseEntity> listCourses() {
        return courses;
    }

    @Override
    public List<CourseEntity> searchCourses(String q) {
        if (q == null || q.isBlank()) return courses;

        String query = q.trim().toLowerCase();
        return courses.stream()
                .filter(c ->
                        (c.getCourseCode() != null && c.getCourseCode().toLowerCase().contains(query)) ||
                        (c.getTitle() != null && c.getTitle().toLowerCase().contains(query)) ||
                        (c.getDescription() != null && c.getDescription().toLowerCase().contains(query))
                )
                .toList();
    }

    private static List<CourseEntity> loadCourses(ObjectMapper mapper) {
        try {
            ClassPathResource res = new ClassPathResource("stub-data/courses.json");
            try (InputStream in = res.getInputStream()) {
                List<CourseJson> rows = mapper.readValue(in, new TypeReference<>() {});
                List<CourseEntity> out = new ArrayList<>();

                for (CourseJson r : rows) {
                    CourseEntity c = new CourseEntity();
                    c.setCourseCode(r.courseCode());
                    c.setTitle(r.title());
                    c.setDescription(r.description());
                    setIdReflective(c, r.id());
                    out.add(c);
                }
                return out;
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load stub-data/courses.json: " + e.getMessage(), e);
        }
    }

    private static void setIdReflective(Object entity, Long id) {
        try {
            var f = entity.getClass().getDeclaredField("id");
            f.setAccessible(true);
            f.set(entity, id);
        } catch (Exception ignored) {}
    }

    private record CourseJson(Long id, String courseCode, String title, String description) {}
}

