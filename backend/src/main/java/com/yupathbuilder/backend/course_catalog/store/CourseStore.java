package com.yupathbuilder.backend.course_catalog.store;

import com.yupathbuilder.backend.course_catalog.entity.CourseEntity;

import java.util.List;

public interface CourseStore {
    List<CourseEntity> listCourses();
    List<CourseEntity> searchCourses(String q);
}

