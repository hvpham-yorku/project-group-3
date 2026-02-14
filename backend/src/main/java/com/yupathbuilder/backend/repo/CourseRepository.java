package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.model.Course;
import java.util.List;
import java.util.Optional;


/**
 * Repository abstraction / implementation: CourseRepository.
 *
 * <p>Handles data access (file-backed CSV, in-memory storage, etc.).
 */

public interface CourseRepository {
    List<Course> findAll();
    Optional<Course> findByNormalizedCode(String normalizedCode);
}
