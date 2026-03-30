package com.yupathbuilder.backend.scheduler_system.store.stub;

import com.yupathbuilder.backend.course_catalog.dto.MeetingDto;

import java.util.List;
import java.util.Map;

/**
 * Holds deterministic meeting patterns used by stub scheduling and course
 * details flows.
 *
 * <p>The goal is not to model real data perfectly, but to provide stable and
 * repeatable schedules across requests in stub mode.</p>
 */
public final class StubTimeRules {

  private StubTimeRules() {}

  /**
   * Synthetic section assignment and meeting list for a single stub course.
   */
  public record Slot(String section, List<MeetingDto> meetings) {}

  private static final Map<String, Slot> FALL = Map.ofEntries(
      // SWE (FALL 2026)
      Map.entry("EECS 1011", new Slot("A", List.of(
          new MeetingDto("MON", "09:30", "10:30", "LAS 1004"),
          new MeetingDto("WED", "09:30", "10:30", "LAS 1004")
      ))),
      Map.entry("MATH 1013", new Slot("A", List.of(
          new MeetingDto("MON", "10:30", "11:30", "LAS 1004"),
          new MeetingDto("WED", "10:30", "11:30", "LAS 1004")
      ))),
      Map.entry("EECS 2030", new Slot("A", List.of(
          new MeetingDto("MON", "12:30", "13:30", "ACW 109"),
          new MeetingDto("WED", "12:30", "13:30", "ACW 109")
      ))),
      Map.entry("EECS 2311", new Slot("A", List.of(
          new MeetingDto("MON", "14:30", "15:30", "ACW 109"),
          new MeetingDto("WED", "14:30", "15:30", "ACW 109")
      ))),
      Map.entry("EECS 3311", new Slot("A", List.of(
          new MeetingDto("TUE", "09:30", "10:30", "CLH 201"),
          new MeetingDto("THU", "09:30", "10:30", "CLH 201")
      ))),
      Map.entry("EECS 3482", new Slot("A", List.of(
          new MeetingDto("TUE", "10:30", "11:30", "CLH 201"),
          new MeetingDto("THU", "10:30", "11:30", "CLH 201")
      ))),
      Map.entry("EECS 4481", new Slot("A", List.of(
          new MeetingDto("TUE", "12:30", "13:30", "LAS 1004"),
          new MeetingDto("THU", "12:30", "13:30", "LAS 1004")
      ))),
      Map.entry("EECS 4482", new Slot("A", List.of(
          new MeetingDto("TUE", "14:30", "15:30", "LAS 1004"),
          new MeetingDto("THU", "14:30", "15:30", "LAS 1004")
      ))),

      // KINE (FALL 2026)
      Map.entry("KINE 1000", new Slot("A", List.of(
          new MeetingDto("FRI", "09:30", "10:30", "HNE 100")
      ))),
      Map.entry("KINE 1020", new Slot("A", List.of(
          new MeetingDto("FRI", "10:30", "11:30", "HNE 100")
      ))),
      Map.entry("KINE 2011", new Slot("A", List.of(
          new MeetingDto("FRI", "12:30", "13:30", "HNE 200")
      ))),
      Map.entry("KINE 2031", new Slot("A", List.of(
          new MeetingDto("FRI", "14:30", "15:30", "HNE 200")
      ))),
      Map.entry("KINE 3012", new Slot("A", List.of(
          new MeetingDto("MON", "16:00", "17:00", "HNE 300")
      ))),
      Map.entry("KINE 3030", new Slot("A", List.of(
          new MeetingDto("WED", "16:00", "17:00", "HNE 300")
      ))),
      Map.entry("KINE 4010", new Slot("A", List.of(
          new MeetingDto("TUE", "16:00", "17:00", "HNE 400")
      ))),
      Map.entry("KINE 4020", new Slot("A", List.of(
          new MeetingDto("THU", "16:00", "17:00", "HNE 400")
      )))
  );

  private static final Map<String, Slot> WINTER = Map.ofEntries(
      // SWE (WINTER 2027)  (swap MW <-> TTh vs FALL)
      Map.entry("EECS 1011", new Slot("A", List.of(
          new MeetingDto("TUE", "09:30", "10:30", "LAS 1004"),
          new MeetingDto("THU", "09:30", "10:30", "LAS 1004")
      ))),
      Map.entry("MATH 1013", new Slot("A", List.of(
          new MeetingDto("TUE", "10:30", "11:30", "LAS 1004"),
          new MeetingDto("THU", "10:30", "11:30", "LAS 1004")
      ))),
      Map.entry("EECS 2030", new Slot("A", List.of(
          new MeetingDto("TUE", "12:30", "13:30", "ACW 109"),
          new MeetingDto("THU", "12:30", "13:30", "ACW 109")
      ))),
      Map.entry("EECS 2311", new Slot("A", List.of(
          new MeetingDto("TUE", "14:30", "15:30", "ACW 109"),
          new MeetingDto("THU", "14:30", "15:30", "ACW 109")
      ))),
      Map.entry("EECS 3311", new Slot("A", List.of(
          new MeetingDto("MON", "09:30", "10:30", "CLH 201"),
          new MeetingDto("WED", "09:30", "10:30", "CLH 201")
      ))),
      Map.entry("EECS 3482", new Slot("A", List.of(
          new MeetingDto("MON", "10:30", "11:30", "CLH 201"),
          new MeetingDto("WED", "10:30", "11:30", "CLH 201")
      ))),
      Map.entry("EECS 4481", new Slot("A", List.of(
          new MeetingDto("MON", "12:30", "13:30", "LAS 1004"),
          new MeetingDto("WED", "12:30", "13:30", "LAS 1004")
      ))),
      Map.entry("EECS 4482", new Slot("A", List.of(
          new MeetingDto("MON", "14:30", "15:30", "LAS 1004"),
          new MeetingDto("WED", "14:30", "15:30", "LAS 1004")
      ))),

      // KINE (WINTER 2027) (igual que FALL según tu seed)
      Map.entry("KINE 1000", new Slot("A", List.of(
          new MeetingDto("FRI", "09:30", "10:30", "HNE 100")
      ))),
      Map.entry("KINE 1020", new Slot("A", List.of(
          new MeetingDto("FRI", "10:30", "11:30", "HNE 100")
      ))),
      Map.entry("KINE 2011", new Slot("A", List.of(
          new MeetingDto("FRI", "12:30", "13:30", "HNE 200")
      ))),
      Map.entry("KINE 2031", new Slot("A", List.of(
          new MeetingDto("FRI", "14:30", "15:30", "HNE 200")
      ))),
      Map.entry("KINE 3012", new Slot("A", List.of(
          new MeetingDto("MON", "16:00", "17:00", "HNE 300")
      ))),
      Map.entry("KINE 3030", new Slot("A", List.of(
          new MeetingDto("WED", "16:00", "17:00", "HNE 300")
      ))),
      Map.entry("KINE 4010", new Slot("A", List.of(
          new MeetingDto("TUE", "16:00", "17:00", "HNE 400")
      ))),
      Map.entry("KINE 4020", new Slot("A", List.of(
          new MeetingDto("THU", "16:00", "17:00", "HNE 400")
      )))
  );

  /**
   * Returns the stub schedule slot for a course and season, falling back to a
   * generic evening slot when no explicit mapping exists.
   */
  public static Slot slotFor(String courseCode, String season) {
    String code = (courseCode == null) ? "" : courseCode.trim().toUpperCase();
    boolean winter = season != null && season.trim().equalsIgnoreCase("WINTER");

    Slot slot = (winter ? WINTER : FALL).get(code);

    // fallback (si buscas un courseCode que no está en el stub-data)
    if (slot == null) {
      return new Slot("A", List.of(
          new MeetingDto("MON", "18:00", "19:00", "TBA")
      ));
    }
    return slot;
  }
}

