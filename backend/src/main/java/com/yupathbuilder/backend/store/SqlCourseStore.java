package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.entity.CourseEntity;
import com.yupathbuilder.backend.repo.CourseRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!stub")
@ConditionalOnProperty(name = "app.store", havingValue = "sql", matchIfMissing = true)
public class SqlCourseStore implements CourseStore {

    private final CourseRepo courseRepo;

    public SqlCourseStore(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    @Override
    public List<CourseEntity> listCourses() {
        return courseRepo.findAll();
    }

    @Override
    public List<CourseEntity> searchCourses(String q) {
        if (q == null || q.isBlank()) return courseRepo.findAll();

        String query = q.trim().toLowerCase();

        return courseRepo.findAll().stream()
                .filter(c ->
                        (c.getCourseCode() != null && c.getCourseCode().toLowerCase().contains(query)) ||
                        (c.getTitle() != null && c.getTitle().toLowerCase().contains(query)) ||
                        (c.getDescription() != null && c.getDescription().toLowerCase().contains(query))
                )
                .toList();
    }
}