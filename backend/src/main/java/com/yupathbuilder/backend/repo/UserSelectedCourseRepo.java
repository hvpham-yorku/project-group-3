package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.entity.UserSelectedCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSelectedCourseRepo extends JpaRepository<UserSelectedCourseEntity, Long> {

    List<UserSelectedCourseEntity> findByUserIdOrderByTermYearAscTermSeasonAscCourseCourseCodeAsc(Long userId);

    Optional<UserSelectedCourseEntity> findByUserIdAndCourseCourseCode(Long userId, String courseCode);

    void deleteByUserIdAndTermIdAndCourseId(Long userId, Long termId, Long courseId);
}
