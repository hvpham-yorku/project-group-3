package com.yupathbuilder.backend.repo.file;

import com.yupathbuilder.backend.model.Course;
import com.yupathbuilder.backend.repo.CourseRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Repository abstraction / implementation: FileCourseRepository.
 *
 * <p>Handles data access (file-backed CSV, in-memory storage, etc.).
 */

@Repository
public class FileCourseRepository implements CourseRepository {

    private final AtomicReference<Map<String, Course>> byCode = new AtomicReference<>(Map.of());
    private final AtomicReference<List<Course>> all = new AtomicReference<>(List.of());

    public FileCourseRepository() {
        reload();
    }

    public synchronized void reload() {
        List<Course> courses = new ArrayList<>();
        Map<String, Course> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("data/courses.csv").getInputStream(), StandardCharsets.UTF_8))) {

            //String header = br.readLine(); // skip
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                List<String> cols = Csv.parseLine(line);

                // Expected: CourseCode,Title,Credits,Department,Prerequisites
                String code = get(cols, 0);
                String title = get(cols, 1);
                double credits = parseDouble(get(cols, 2));
                String dept = get(cols, 3);
                String prereq = get(cols, 4);

                Course c = new Course(code, title, credits, dept, prereq);
                courses.add(c);
                map.put(c.normalizedCode(), c);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load courses.csv from classpath", e);
        }

        courses.sort(Comparator.comparing(Course::normalizedCode));
        byCode.set(Collections.unmodifiableMap(map));
        all.set(Collections.unmodifiableList(courses));
    }

    @Override
    public List<Course> findAll() {
        return all.get();
    }

    @Override
    public Optional<Course> findByNormalizedCode(String normalizedCode) {
        if (normalizedCode == null) return Optional.empty();
        return Optional.ofNullable(byCode.get().get(normalizedCode.replaceAll("\\s+", "").toUpperCase()));
    }

    private static String get(List<String> cols, int idx) {
        return idx < cols.size() ? cols.get(idx) : "";
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
    }
}
