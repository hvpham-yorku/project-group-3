package com.yupathbuilder.backend.course_catalog.store;

import com.yupathbuilder.backend.course_catalog.entity.CourseEntity;

import java.util.List;

/**
 * Abstraction over course catalog storage implementations.
 */
public interface CourseStore {
    /**
     * Returns the full catalog available in the active backing store.
     */
    List<CourseEntity> listCourses();

    /**
     * Searches the active store for courses matching the supplied query.
     */
    List<CourseEntity> searchCourses(String q);
}

