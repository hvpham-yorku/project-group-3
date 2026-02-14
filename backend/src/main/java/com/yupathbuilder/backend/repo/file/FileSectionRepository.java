package com.yupathbuilder.backend.repo.file;

import com.yupathbuilder.backend.model.Section;
import com.yupathbuilder.backend.repo.SectionRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Repository abstraction / implementation: FileSectionRepository.
 *
 * <p>Handles data access (file-backed CSV, in-memory storage, etc.).
 */

@Repository
public class FileSectionRepository implements SectionRepository {

    private final AtomicReference<List<Section>> all = new AtomicReference<>(List.of());

    public FileSectionRepository() {
        reload();
    }
    /**
     * Loads sections from classpath: resources/data/sections.csv
     *
     * <p>This is called once at startup, but can also be triggered from an admin endpoint
     * if you add one later.
     */
    public synchronized void reload() {
        List<Section> sections = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("data/sections.csv").getInputStream(), StandardCharsets.UTF_8))) {

            // First line is the CSV header row.
            br.readLine(); // header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                // Csv.parseLine handles commas inside quotes.
                List<String> cols = Csv.parseLine(line);

                // Expected: SectionId,CourseCode,Term,Instructor,Days,StartTime,EndTime,Location,Capacity
                String sectionId = get(cols, 0);
                String courseCode = get(cols, 1);
                String term = get(cols, 2);
                String instructor = get(cols, 3);
                String days = get(cols, 4);
                LocalTime start = parseTime(get(cols, 5));
                LocalTime end = parseTime(get(cols, 6));
                String location = get(cols, 7);
                int cap = parseInt(get(cols, 8));

                sections.add(new Section(sectionId, courseCode, term, instructor, days, start, end, location, cap));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load sections.csv from classpath", e);
        }

        all.set(Collections.unmodifiableList(sections));
    }

    @Override
    public List<Section> findAll() {
        return all.get();
    }

    @Override
    public List<Section> findByNormalizedCourseCodeAndTerm(String normalizedCourseCode, String term) {
        String code = normalizedCourseCode == null ? "" : normalizedCourseCode.replaceAll("\\s+", "").toUpperCase();
        String t = term == null ? "" : term.trim();
        List<Section> out = new ArrayList<>();
        for (Section s : all.get()) {
            if (s.normalizedCourseCode().equals(code) && (t.isBlank() || t.equalsIgnoreCase(s.term()))) {
                out.add(s);
            }
        }
        out.sort(Comparator.comparing(Section::sectionId));
        return out;
    }

    private static String get(List<String> cols, int idx) { return idx < cols.size() ? cols.get(idx) : ""; }

        // CSV times are stored as ISO-8601 (HH:MM or HH:MM:SS). Invalid/missing times become null.
    private static LocalTime parseTime(String s) {
        try {
            if (s == null || s.isBlank()) return null;
            return LocalTime.parse(s.trim());
        } catch (Exception e) {
            return null;
        }
    }
    private static int parseInt(String s) { try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; } }
}
