package com.yupathbuilder.backend.scheduler_system.repo;

import com.yupathbuilder.backend.scheduler_system.entity.UserSelectedCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Persistence gateway for user-selected course records.
 */
public interface UserSelectedCourseRepo extends JpaRepository<UserSelectedCourseEntity, Long> {

    /**
     * Returns a user's saved selections ordered by term and course code.
     */
    List<UserSelectedCourseEntity> findByUserIdOrderByTermYearAscTermSeasonAscCourseCourseCodeAsc(Long userId);

    /**
     * Resolves whether a user has already saved a course, regardless of term.
     */
    Optional<UserSelectedCourseEntity> findByUserIdAndCourseCourseCode(Long userId, String courseCode);

    /**
     * Removes a saved course selection using its natural key components.
     */
    void deleteByUserIdAndTermIdAndCourseId(Long userId, Long termId, Long courseId);
}


