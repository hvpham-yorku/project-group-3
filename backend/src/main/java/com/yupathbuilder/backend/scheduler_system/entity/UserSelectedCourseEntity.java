package com.yupathbuilder.backend.scheduler_system.entity;

import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.course_catalog.entity.CourseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * JPA entity representing a course the user has explicitly saved for later
 * scheduling.
 */
@Entity
@Table(
    name = "user_selected_courses",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_user_selected_courses_user_course", columnNames = {"user_id", "course_id"}),
        @UniqueConstraint(name = "uq_user_selected_courses_user_term_course", columnNames = {"user_id", "term_id", "course_id"})
    }
)
public class UserSelectedCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "term_id", nullable = false)
    private TermEntity term;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TermEntity getTerm() {
        return term;
    }

    public void setTerm(TermEntity term) {
        this.term = term;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

