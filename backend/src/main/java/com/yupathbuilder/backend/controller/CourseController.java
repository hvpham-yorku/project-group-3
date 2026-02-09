package com.yupathbuilder.backend.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yupathbuilder.backend.model.Course;
import com.yupathbuilder.backend.repo.CourseRepository;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepo;

    public CourseController(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    // GET /api/courses
    @GetMapping
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }

    // GET /api/courses/{code}
    @GetMapping("/{code}")
    public ResponseEntity<Course> getCourseByCode(@PathVariable String code) {
        return courseRepo.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/courses
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        if (course == null || isBlank(course.getCode()) || isBlank(course.getTitle())) {
            return ResponseEntity.badRequest().body("Missing required fields: code, title");
        }
        if (course.getCredits() <= 0) {
            return ResponseEntity.badRequest().body("credits must be > 0");
        }

        // prevent duplicates
        if (courseRepo.findByCode(course.getCode()).isPresent()) {
            return ResponseEntity.status(409).body("Course already exists: " + course.getCode());
        }

        courseRepo.save(course);

        // Location header points to the new resource
        URI location = URI.create("/api/courses/" + course.getCode().trim());
        return ResponseEntity.created(location).body(course);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> updateCourse(@PathVariable String code, @RequestBody Course updated) {
        if (updated == null || isBlank(updated.getTitle()) || updated.getCredits() <= 0) {
          return ResponseEntity.badRequest().body("Missing/invalid fields");
        }

    String normalized = code.trim().toUpperCase();
    return courseRepo.findByCode(normalized)
        .map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setCredits(updated.getCredits());
            existing.setPrerequisites(updated.getPrerequisites());
            courseRepo.save(existing);
            return ResponseEntity.ok(existing);
        })
        .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteCourse(@PathVariable String code) {
        boolean deleted = courseRepo.deleteByCode(code);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
