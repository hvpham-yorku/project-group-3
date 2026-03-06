package com.yupathbuilder.backend.store.stub;

import com.yupathbuilder.backend.dto.MeetingDto;
import com.yupathbuilder.backend.schedule.dto.ChosenSectionDto;
import com.yupathbuilder.backend.schedule.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.store.ScheduleStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.store", havingValue = "stub")
public class StubScheduleStore implements ScheduleStore {

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