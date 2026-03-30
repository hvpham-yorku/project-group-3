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

/**
 * Stub-backed implementation of the course catalog store.
 *
 * <p>This store loads a fixed catalog snapshot from bundled JSON so the
 * application can run without a database.</p>
 */
@Component
@ConditionalOnProperty(name = "app.store", havingValue = "stub")
public class StubCourseStore implements CourseStore {

    private final List<CourseEntity> courses;

    public StubCourseStore(ObjectMapper mapper) {
        this.courses = Collections.unmodifiableList(loadCourses(mapper));
    }

    /**
     * Returns the immutable in-memory catalog snapshot loaded at startup.
     */
    @Override
    public List<CourseEntity> listCourses() {
        return courses;
    }

    /**
     * Performs the same free-text matching rules as the SQL-backed
     * implementation, but against in-memory stub data.
     */
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

    /**
     * Loads stub course data and adapts it into the entity shape used by the
     * rest of the application.
     */
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

    /**
     * Assigns entity IDs reflectively so stub data can mimic persisted records
     * without adding test-only setters.
     */
    private static void setIdReflective(Object entity, Long id) {
        try {
            var f = entity.getClass().getDeclaredField("id");
            f.setAccessible(true);
            f.set(entity, id);
        } catch (Exception ignored) {}
    }

    /**
     * JSON shape used to deserialize stub course records.
     */
    private record CourseJson(Long id, String courseCode, String title, String description) {}
}

