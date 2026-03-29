package com.yupathbuilder.backend.service;

import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.repo.UserRepo;
import com.yupathbuilder.backend.entity.CourseEntity;
import com.yupathbuilder.backend.entity.TermEntity;
import com.yupathbuilder.backend.entity.UserSelectedCourseEntity;
import com.yupathbuilder.backend.model.Season;
import com.yupathbuilder.backend.repo.CourseRepo;
import com.yupathbuilder.backend.repo.TermRepo;
import com.yupathbuilder.backend.repo.UserSelectedCourseRepo;
import com.yupathbuilder.backend.selection.dto.SavedCourseSelectionDto;
import com.yupathbuilder.backend.util.TermParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

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

    public SavedCourseSelectionDto saveSelection(String email, String termString, String courseCode) {
        UserEntity user = getUserByEmail(email);
        TermEntity term = getTerm(termString);
        CourseEntity course = getCourse(courseCode);
        String normalizedCourseCode = course.getCourseCode();

        UserSelectedCourseEntity existing = userSelectedCourseRepo
                .findByUserIdAndCourseCourseCode(user.getId(), normalizedCourseCode)
                .orElse(null);

        if (existing != null) {
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

    public void removeSelection(String email, String termString, String courseCode) {
        UserEntity user = getUserByEmail(email);
        TermEntity term = getTerm(termString);
        CourseEntity course = getCourse(courseCode);

        userSelectedCourseRepo.deleteByUserIdAndTermIdAndCourseId(user.getId(), term.getId(), course.getId());
    }

    private SavedCourseSelectionDto toDto(UserSelectedCourseEntity entity) {
        return new SavedCourseSelectionDto(
                formatTerm(entity.getTerm()),
                entity.getCourse().getCourseCode()
        );
    }

    private UserEntity getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private TermEntity getTerm(String termString) {
        var termKey = TermParser.parse(termString);
        TermEntity term = termRepo.findBySeasonAndYear(termKey.season(), termKey.year());
        if (term == null) {
            throw new IllegalArgumentException("Term not found: " + termString);
        }
        return term;
    }

    private CourseEntity getCourse(String courseCode) {
        String normalizedCourseCode = normalizeCourseCode(courseCode);
        CourseEntity course = courseRepo.findByCourseCode(normalizedCourseCode);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + normalizedCourseCode);
        }
        return course;
    }

    private String normalizeCourseCode(String courseCode) {
        if (courseCode == null || courseCode.trim().isBlank()) {
            throw new IllegalArgumentException("courseCode is required");
        }
        return courseCode.trim().toUpperCase();
    }

    private String formatTerm(TermEntity term) {
        return term.getSeason().name() + " " + term.getYear();
    }

    private int compareTermStrings(String left, String right) {
        var leftTerm = TermParser.parse(left);
        var rightTerm = TermParser.parse(right);

        int yearCompare = Integer.compare(leftTerm.year(), rightTerm.year());
        if (yearCompare != 0) {
            return yearCompare;
        }
        return Integer.compare(seasonOrder(leftTerm.season()), seasonOrder(rightTerm.season()));
    }

    private int seasonOrder(Season season) {
        return switch (season) {
            case WINTER -> 1;
            case SUMMER -> 2;
            case FALL -> 3;
        };
    }
}
