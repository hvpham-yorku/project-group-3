package com.yupathbuilder.backend.schedule;

import com.yupathbuilder.backend.model.Section;
import com.yupathbuilder.backend.repo.SectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Service layer component: ScheduleService.
 *
 * <p>Contains business logic (e.g., searching courses, building schedules, detecting conflicts).
 */

@Service
public class ScheduleService {

    private final SectionRepository sections;

    public ScheduleService(SectionRepository sections) {
        this.sections = sections;
    }
    /**
     * Attempts to build a non-conflicting schedule by choosing exactly one section per course.
     *
     * <p>Returns {@code Optional.empty()} if:
     * <ul>
     *   <li>any requested course has no available sections for the term, or</li>
     *   <li>no combination of sections can be found without time overlaps.</li>
     * </ul>
     */
    public Optional<List<Section>> build(String term, List<String> courseCodes) {
        // Normalize user input so the scheduler is case/spacing-insensitive.
// Example: " eecs 2030 " -> "EECS2030"
        List<String> normalized = courseCodes.stream()
                .filter(Objects::nonNull)
                .map(s -> s.replaceAll("\s+", "").toUpperCase())
                .distinct()
                .toList();

        // collect candidate sections per course
        List<List<Section>> candidates = new ArrayList<>();
        for (String code : normalized) {
            List<Section> list = sections.findByNormalizedCourseCodeAndTerm(code, term);
            if (list.isEmpty()) return Optional.empty();
            // stable ordering: prefer earlier start, then sectionId
            list = new ArrayList<>(list);
            list.sort(Comparator
                    .comparing((Section s) -> s.startTime() == null ? LocalTime.MAX : s.startTime())
                    .thenComparing(Section::sectionId));
            candidates.add(list);
        }

        // backtracking
        List<Section> chosen = new ArrayList<>();
        Map<Character, List<TimeRange>> occupied = new HashMap<>();
        for (char d : List.of('M','T','W','R','F','S','U')) occupied.put(d, new ArrayList<>());

        boolean ok = backtrack(0, candidates, chosen, occupied);
        if (!ok) return Optional.empty();
        return Optional.of(List.copyOf(chosen));
    }
    /**
     * Depth-first search over the cartesian product of candidate sections.
     * We keep a per-day list of occupied time ranges to prune early when conflicts appear.
     */
    private boolean backtrack(int i, List<List<Section>> candidates, List<Section> chosen,
                              Map<Character, List<TimeRange>> occupied) {
        if (i == candidates.size()) return true;

        for (Section s : candidates.get(i)) {
            if (fits(s, occupied)) {
                chosen.add(s);
                List<Character> days = add(s, occupied);
                if (backtrack(i + 1, candidates, chosen, occupied)) return true;
                remove(s, days, occupied);
                chosen.remove(chosen.size() - 1);
            }
        }
        return false;
    }

    private boolean fits(Section s, Map<Character, List<TimeRange>> occupied) {
        if (s.startTime() == null || s.endTime() == null) return true; // treat missing as non-conflicting
        for (char day : parseDays(s.days())) {
            for (TimeRange r : occupied.getOrDefault(day, List.of())) {
                if (r.overlaps(s.startTime(), s.endTime())) return false;
            }
        }
        return true;
    }

    private List<Character> add(Section s, Map<Character, List<TimeRange>> occupied) {
        List<Character> days = parseDays(s.days());
        if (s.startTime() == null || s.endTime() == null) return days;
        for (char d : days) occupied.get(d).add(new TimeRange(s.startTime(), s.endTime(), s.sectionId()));
        return days;
    }

    private void remove(Section s, List<Character> days, Map<Character, List<TimeRange>> occupied) {
        if (s.startTime() == null || s.endTime() == null) return;
        for (char d : days) {
            occupied.get(d).removeIf(r -> Objects.equals(r.sectionId, s.sectionId()));
        }
    }
    /**
     * Parses compact day strings like "MW" or "TR" into individual day codes.
     * We follow York convention where 'R' means Thursday.
     */
    private List<Character> parseDays(String days) {
        if (days == null) return List.of();
        String d = days.trim().toUpperCase();
        List<Character> out = new ArrayList<>();
        for (int i = 0; i < d.length(); i++) {
            char c = d.charAt(i);
            if ("MTWRFSU".indexOf(c) >= 0) out.add(c);
        }
        return out;
    }

    /**
     * Detects conflicts among sections for the given courses/term.
     * Reports all pairs of courses where at least some sections overlap,
     * showing the specific time clashes.
     */
    public List<String> findConflicts(String term, List<String> courseCodes) {
        List<String> normalized = courseCodes.stream()
                .filter(Objects::nonNull)
                .map(s -> s.replaceAll("\\s+", "").toUpperCase())
                .distinct()
                .toList();

        // collect ALL candidate sections per course
        Map<String, List<Section>> candidateMap = new LinkedHashMap<>();
        for (String code : normalized) {
            List<Section> list = sections.findByNormalizedCourseCodeAndTerm(code, term);
            if (!list.isEmpty()) {
                candidateMap.put(code, list);
            }
        }

        List<String> conflicts = new ArrayList<>();
        List<String> codes = new ArrayList<>(candidateMap.keySet());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("h:mm a");

        // Check every pair of courses and report overlapping section combos
        for (int i = 0; i < codes.size(); i++) {
            for (int j = i + 1; j < codes.size(); j++) {
                String codeA = codes.get(i);
                String codeB = codes.get(j);
                List<Section> sectionsA = candidateMap.get(codeA);
                List<Section> sectionsB = candidateMap.get(codeB);

                // Collect all conflicting pairs between these two courses
                Set<String> reported = new HashSet<>();
                for (Section a : sectionsA) {
                    for (Section b : sectionsB) {
                        if (sectionsConflict(a, b)) {
                            String key = a.sectionId() + "|" + b.sectionId();
                            if (reported.add(key)) {
                                String timeA = formatSectionTime(a, fmt);
                                String timeB = formatSectionTime(b, fmt);
                                conflicts.add(String.format(
                                        "%s (%s, %s) conflicts with %s (%s, %s)",
                                        a.courseCode(), a.sectionId(), timeA,
                                        b.courseCode(), b.sectionId(), timeB));
                            }
                        }
                    }
                }
            }
        }

        // Also report courses with no sections in this term
        for (String code : normalized) {
            if (!candidateMap.containsKey(code)) {
                conflicts.add(String.format("%s has no available sections for term %s", code, term));
            }
        }

        return conflicts;
    }

    private boolean sectionsConflict(Section a, Section b) {
        if (a.startTime() == null || a.endTime() == null || b.startTime() == null || b.endTime() == null)
            return false;
        List<Character> daysA = parseDays(a.days());
        List<Character> daysB = parseDays(b.days());
        for (char da : daysA) {
            for (char db : daysB) {
                if (da == db && a.startTime().isBefore(b.endTime()) && b.startTime().isBefore(a.endTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String formatSectionTime(Section s, DateTimeFormatter fmt) {
        String days = s.days() != null ? s.days() : "?";
        String start = s.startTime() != null ? s.startTime().format(fmt) : "?";
        String end = s.endTime() != null ? s.endTime().format(fmt) : "?";
        return days + " " + start + "-" + end;
    }

    private static class TimeRange {
        final LocalTime start;
        final LocalTime end;
        final String sectionId;

        TimeRange(LocalTime start, LocalTime end, String sectionId) {
            this.start = start;
            this.end = end;
            this.sectionId = sectionId;
        }

        boolean overlaps(LocalTime s, LocalTime e) {
            // overlap if start < otherEnd and otherStart < end
            return start.isBefore(e) && s.isBefore(end);
        }
    }
}
