package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.entity.CourseEntity;

import java.util.List;

public interface CourseStore {
    List<CourseEntity> listCourses();
    List<CourseEntity> searchCourses(String q);
}