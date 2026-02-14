package com.yupathbuilder.backend.service;

import com.yupathbuilder.backend.model.Conflict;
import com.yupathbuilder.backend.model.Section;
import com.yupathbuilder.backend.model.TimeSlot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Service layer component: ConflictDetector.
 *
 * <p>Contains business logic (e.g., searching courses, building schedules, detecting conflicts).
 */

public class ConflictDetector {

    public List<Conflict> detectConflicts(List<Section> existing, Section incoming) {
        List<Conflict> conflicts = new ArrayList<>();
        if (incoming == null || existing == null) return conflicts;

        if (incoming.days() == null || incoming.startTime() == null || incoming.endTime() == null) return conflicts;

        Set<TimeSlot.Day> incomingDays = parseDays(incoming.days());

        for (Section ex : existing) {
            if (ex == null) continue;
            if (ex.days() == null || ex.startTime() == null || ex.endTime() == null) continue;

            Set<TimeSlot.Day> exDays = parseDays(ex.days());

            for (TimeSlot.Day day : exDays) {
                if (!incomingDays.contains(day)) continue;

                if (overlaps(ex.startTime(), ex.endTime(), incoming.startTime(), incoming.endTime())) {
                    LocalTime clashStart = max(ex.startTime(), incoming.startTime());
                    LocalTime clashEnd = min(ex.endTime(), incoming.endTime());

                    int startMin = toMinutes(clashStart);
                    int endMin = toMinutes(clashEnd);

                    // ensure valid TimeSlot: endMin must be > startMin
                    if (endMin > startMin) {
                        TimeSlot clashOn = new TimeSlot(day, startMin, endMin);
                        conflicts.add(new Conflict(ex, incoming, clashOn));
                    }
                }
            }
        }

        return conflicts;
    }

    private boolean overlaps(LocalTime aStart, LocalTime aEnd, LocalTime bStart, LocalTime bEnd) {
        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }

    private LocalTime max(LocalTime a, LocalTime b) {
        return a.isAfter(b) ? a : b;
    }

    private LocalTime min(LocalTime a, LocalTime b) {
        return a.isBefore(b) ? a : b;
    }

    private int toMinutes(LocalTime t) {
        return t.getHour() * 60 + t.getMinute();
    }

    private Set<TimeSlot.Day> parseDays(String days) {
        Set<TimeSlot.Day> out = new HashSet<>();
        if (days == null) return out;

        // expected like "Mon,Wed" (your comment). Also handle "MON,WED" etc.
        String[] parts = days.replace(" ", "").split(",");

        for (String p : parts) {
            TimeSlot.Day d = toDay(p);
            if (d != null) out.add(d);
        }
        return out;
    }

    private TimeSlot.Day toDay(String s) {
        if (s == null) return null;
        String x = s.trim().toUpperCase();

        // accept Mon/Tue/... or MON/TUE/...
        return switch (x) {
            case "MON", "MONDAY" -> TimeSlot.Day.MON;
            case "TUE", "TUES", "TUESDAY" -> TimeSlot.Day.TUE;
            case "WED", "WEDNESDAY" -> TimeSlot.Day.WED;
            case "THU", "THUR", "THURS", "THURSDAY" -> TimeSlot.Day.THU;
            case "FRI", "FRIDAY" -> TimeSlot.Day.FRI;
            case "SAT", "SATURDAY" -> TimeSlot.Day.SAT;
            case "SUN", "SUNDAY" -> TimeSlot.Day.SUN;
            default -> null;
        };
    }
}
