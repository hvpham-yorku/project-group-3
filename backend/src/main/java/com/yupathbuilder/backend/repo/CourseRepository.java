package com.yupathbuilder.backend.repo;

import java.util.List;
import java.util.Optional;
import com.yupathbuilder.backend.model.Course;

public interface CourseRepository {
    List<Course> findAll();
    Optional<Course> findByCode(String code);

    Course save(Course course);      // <-- solo este
    boolean deleteByCode(String code);
}
