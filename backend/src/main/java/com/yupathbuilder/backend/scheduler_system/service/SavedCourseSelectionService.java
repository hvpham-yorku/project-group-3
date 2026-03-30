package com.yupathbuilder.backend.scheduler_system.service;

import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.repo.UserRepo;
import com.yupathbuilder.backend.course_catalog.entity.CourseEntity;
import com.yupathbuilder.backend.scheduler_system.entity.TermEntity;
import com.yupathbuilder.backend.scheduler_system.entity.UserSelectedCourseEntity;
import com.yupathbuilder.backend.scheduler_system.model.Season;
import com.yupathbuilder.backend.course_catalog.repo.CourseRepo;
import com.yupathbuilder.backend.scheduler_system.repo.TermRepo;
import com.yupathbuilder.backend.scheduler_system.repo.UserSelectedCourseRepo;
import com.yupathbuilder.backend.scheduler_system.dto.SavedCourseSelectionDto;
import com.yupathbuilder.backend.util.TermParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * Manages persistence of the authenticated user's selected courses.
 *
 * <p>This service validates user identity, term existence, and course
 * uniqueness before writing selections to the database.</p>
 */
@Service
@Transactional
public class SavedCourseSelectionService {

    private final UserRepo userRepo;
    private final TermRepo termRepo;
    private final CourseRepo courseRepo;
    private final UserSelectedCourseRepo userSelectedCourseRepo;

    public SavedCourseSelectionService(
            UserRepo userRepo,
            TermRepo termRepo,
            CourseRepo courseRepo,
            UserSelectedCourseRepo userSelectedCourseRepo
    ) {
        this.userRepo = userRepo;
        this.termRepo = termRepo;
        this.courseRepo = courseRepo;
        this.userSelectedCourseRepo = userSelectedCourseRepo;
    }

    /**
     * Returns the current user's saved course selections ordered by term and
     * course code.
     */
    @Transactional(readOnly = true)
    public List<SavedCourseSelectionDto> listSelections(String email) {
        UserEntity user = getUserByEmail(email);

        return userSelectedCourseRepo.findByUserIdOrderByTermYearAscTermSeasonAscCourseCourseCodeAsc(user.getId())
                .stream()
                .map(this::toDto)
                .sorted(Comparator
                        .comparing(SavedCourseSelectionDto::term, this::compareTermStrings)
                        .thenComparing(SavedCourseSelectionDto::courseCode))
                .toList();
    }

    /**
     * Saves a course selection for a user, enforcing one saved term per course.
     */
    public SavedCourseSelectionDto saveSelection(String email, String termString, String courseCode) {
        UserEntity user = getUserByEmail(email);
        TermEntity term = getTerm(termString);
        CourseEntity course = getCourse(courseCode);
        String normalizedCourseCode = course.getCourseCode();

        UserSelectedCourseEntity existing = userSelectedCourseRepo
                .findByUserIdAndCourseCourseCode(user.getId(), normalizedCourseCode)
                .orElse(null);

        if (existing != null) {
            // The same course cannot be saved into multiple terms for the same user.
            if (existing.getTerm().getId().equals(term.getId())) {
                return toDto(existing);
            }
            throw new IllegalArgumentException(
                    normalizedCourseCode + " is already saved in " + formatTerm(existing.getTerm())
            );
        }

        UserSelectedCourseEntity entity = new UserSelectedCourseEntity();
        entity.setUser(user);
        entity.setTerm(term);
        entity.setCourse(course);

        return toDto(userSelectedCourseRepo.save(entity));
    }

    /**
     * Removes a saved course selection for the given user, term, and course.
     */
    public void removeSelection(String email, String termString, String courseCode) {
        UserEntity user = getUserByEmail(email);
        TermEntity term = getTerm(termString);
        CourseEntity course = getCourse(courseCode);

        userSelectedCourseRepo.deleteByUserIdAndTermIdAndCourseId(user.getId(), term.getId(), course.getId());
    }

    /**
     * Converts a persisted selection into the API-facing DTO.
     */
    private SavedCourseSelectionDto toDto(UserSelectedCourseEntity entity) {
        return new SavedCourseSelectionDto(
                formatTerm(entity.getTerm()),
                entity.getCourse().getCourseCode()
        );
    }

    /**
     * Resolves a user by email and fails fast when the authenticated principal
     * is not backed by a persisted account.
     */
    private UserEntity getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * Resolves a term string into a persisted term entity.
     */
    private TermEntity getTerm(String termString) {
        var termKey = TermParser.parse(termString);
        TermEntity term = termRepo.findBySeasonAndYear(termKey.season(), termKey.year());
        if (term == null) {
            throw new IllegalArgumentException("Term not found: " + termString);
        }
        return term;
    }

    /**
     * Resolves and normalizes a course code against the catalog.
     */
    private CourseEntity getCourse(String courseCode) {
        String normalizedCourseCode = normalizeCourseCode(courseCode);
        CourseEntity course = courseRepo.findByCourseCode(normalizedCourseCode);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + normalizedCourseCode);
        }
        return course;
    }

    /**
     * Normalizes incoming course codes before repository lookups and
     * uniqueness checks.
     */
    private String normalizeCourseCode(String courseCode) {
        if (courseCode == null || courseCode.trim().isBlank()) {
            throw new IllegalArgumentException("courseCode is required");
        }
        return courseCode.trim().toUpperCase();
    }

    /**
     * Formats a term entity into the canonical API term string.
     */
    private String formatTerm(TermEntity term) {
        return term.getSeason().name() + " " + term.getYear();
    }

    /**
     * Comparator helper that orders term strings chronologically.
     */
    private int compareTermStrings(String left, String right) {
        var leftTerm = TermParser.parse(left);
        var rightTerm = TermParser.parse(right);

        int yearCompare = Integer.compare(leftTerm.year(), rightTerm.year());
        if (yearCompare != 0) {
            return yearCompare;
        }
        return Integer.compare(seasonOrder(leftTerm.season()), seasonOrder(rightTerm.season()));
    }

    /**
     * Defines the academic year ordering used when sorting saved selections.
     */
    private int seasonOrder(Season season) {
        return switch (season) {
            case WINTER -> 1;
            case SUMMER -> 2;
            case FALL -> 3;
        };
    }
}


