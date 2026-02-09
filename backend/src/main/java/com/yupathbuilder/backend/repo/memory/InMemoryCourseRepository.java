package com.yupathbuilder.backend.repo.memory;

import com.yupathbuilder.backend.model.Course;
import com.yupathbuilder.backend.repo.CourseRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryCourseRepository implements CourseRepository {

    private final Map<String, Course> courses = new HashMap<>();

    public InMemoryCourseRepository() {
        seed();
    }

    private void seed() {
        save(new Course("EECS 2311", "Software Development Project", 3.0));
        save(new Course("EECS 2030", "Advanced Object Oriented Programming", 3.0));
        save(new Course("EECS 2011", "Fundamentals of Data Structures", 3.0));
        save(new Course("EECS 2021", "Computer Organization", 3.0));
        save(new Course("EECS 3311", "Software Design", 3.0));
        save(new Course("MATH 1014", "Applied Calculus I", 3.0));
        save(new Course("MATH 1013", "Applied Calculus II", 3.0));
        save(new Course("PHYS 1800", "Physics: Waves & Fields", 3.0));
    }

    private String normalizeCode(String code) {
        if (code == null) return null;
        return code.replaceAll("\\s+", "").toUpperCase(); // "EECS 3311" -> "EECS3311"
    }

    @Override
    public List<Course> findAll() {
        List<Course> list = new ArrayList<>(courses.values());
        list.sort(Comparator.comparing(Course::getCode));
        return list;
    }

    @Override
    public Optional<Course> findByCode(String code) {
        String key = normalizeCode(code);
        if (key == null) return Optional.empty();
        return Optional.ofNullable(courses.get(key));
    }

    @Override
    public Course save(Course course) {
        if (course == null) return null;

        String key = normalizeCode(course.getCode());
        if (key == null) return null;

        // si quieres guardar "EECS3311" como code interno:
        course.setCode(key);

        courses.put(key, course);
        return course;
    }

    @Override
    public boolean deleteByCode(String code) {
        String key = normalizeCode(code);
        if (key == null) return false;
        return courses.remove(key) != null;
    }
    
}
