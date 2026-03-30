package com.yupathbuilder.backend.scheduler_system.store.stub;

import com.yupathbuilder.backend.course_catalog.dto.MeetingDto;
import com.yupathbuilder.backend.scheduler_system.dto.ChosenSectionDto;
import com.yupathbuilder.backend.scheduler_system.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.scheduler_system.store.ScheduleStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub-backed schedule store used when the application runs without a
 * database.
 *
 * <p>This implementation builds deterministic schedules from hard-coded stub
 * time rules so frontend flows remain testable.</p>
 */
@Component
@ConditionalOnProperty(name = "app.store", havingValue = "stub")
public class StubScheduleStore implements ScheduleStore {

  /**
   * Builds a deterministic schedule from stub time rules.
   */
  @Override
  public ScheduleBuildResponse build(String termString, List<String> courseCodes) {
    if (courseCodes == null) courseCodes = List.of();

    String season = parseSeasonFromTerm(termString);

    List<ChosenSectionDto> chosen = new ArrayList<>();

    for (String code : courseCodes) {
      if (code == null || code.isBlank()) continue;

      String courseCode = code.trim();
      var slot = StubTimeRules.slotFor(courseCode, season);

      MeetingDto first = slot.meetings().get(0);
      String days = String.join(",", slot.meetings().stream().map(MeetingDto::day).distinct().toList());

      chosen.add(new ChosenSectionDto(
          courseCode,
          slot.section(),
          days,
          first.startTime(),
          first.endTime(),
          first.location()
      ));
    }

    return new ScheduleBuildResponse(termString, chosen);
  }

  /**
   * Extracts the season token from a user-facing term string.
   */
  private String parseSeasonFromTerm(String termString) {
    if (termString == null) return "FALL";
    String t = termString.trim().toUpperCase();

    if (t.startsWith("WINTER")) return "WINTER";
    if (t.startsWith("FALL")) return "FALL";
    if (t.startsWith("SUMMER")) return "SUMMER";

    String[] parts = t.split("[\\s\\-]+");
    return parts.length > 0 ? parts[0] : "FALL";
  }
}

