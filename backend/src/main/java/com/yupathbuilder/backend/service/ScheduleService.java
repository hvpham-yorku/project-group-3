package com.yupathbuilder.backend.service;

import com.yupathbuilder.backend.schedule.dto.ChosenSectionDto;
import com.yupathbuilder.backend.schedule.dto.ScheduleBuildResponse;
import com.yupathbuilder.backend.repo.SectionRepo;
import com.yupathbuilder.backend.repo.TermRepo;
import com.yupathbuilder.backend.util.TermParser;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class ScheduleService {

  private final TermRepo termRepo;
  private final SectionRepo sectionRepo;

  public ScheduleService(TermRepo termRepo, SectionRepo sectionRepo) {
    this.termRepo = termRepo;
    this.sectionRepo = sectionRepo;
  }

  public ScheduleBuildResponse build(String termString, List<String> courseCodes) {
    var termKey = TermParser.parse(termString);
    var term = termRepo.findBySeasonAndYear(termKey.season(), termKey.year());
    if (term == null) throw new IllegalArgumentException("Term not found: " + termString);

    List<ChosenSectionDto> chosen = new ArrayList<>();
    List<Block> used = new ArrayList<>();

    for (String code : courseCodes) {
      if (code == null || code.isBlank()) continue;

      var sections = sectionRepo.findSectionsWithMeetings(code, term.getSeason(), term.getYear());
      if (sections.isEmpty()) {
        throw new IllegalArgumentException("No sections for " + code + " in " + termString);
      }

      // pick first non-conflicting section
      var picked = sections.stream()
          .filter(s -> s.getMeetings() != null && !s.getMeetings().isEmpty())
          .filter(s -> !conflicts(toBlocks(s), used))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("No non-conflicting section found for " + code));

      var pickedBlocks = toBlocks(picked);
      used.addAll(pickedBlocks);

      chosen.add(toChosenDto(picked.getCourse().getCourseCode(), picked.getSectionCode(), pickedBlocks));
    }

    return new ScheduleBuildResponse(termString, chosen);
  }

  private boolean conflicts(List<Block> a, List<Block> b) {
    for (var x : a) for (var y : b) if (x.conflicts(y)) return true;
    return false;
  }

  private List<Block> toBlocks(com.yupathbuilder.backend.entity.SectionEntity s) {
    var out = new ArrayList<Block>();
    for (var m : s.getMeetings()) {
      out.add(new Block(m.getDayOfWeek(), m.getStartTime(), m.getEndTime(), m.getLocation()));
    }
    return out;
  }

  private ChosenSectionDto toChosenDto(String courseCode, String sectionCode, List<Block> blocks) {
    // Group meetings by same time slot + location; build "MON,WED"
    var first = blocks.get(0);
    var days = new ArrayList<String>();
    for (var b : blocks) {
      if (sameSlot(first, b)) days.add(b.day);
    }
    String dayStr = String.join(",", days);

    return new ChosenSectionDto(
        courseCode,
        sectionCode,
        dayStr,
        first.start.toString(),
        first.end.toString(),
        first.location
    );
  }

  private boolean sameSlot(Block a, Block b) {
    return Objects.equals(a.start, b.start) &&
        Objects.equals(a.end, b.end) &&
        Objects.equals(a.location, b.location);
  }

  private record Block(String day, LocalTime start, LocalTime end, String location) {
    boolean conflicts(Block o) {
      if (!Objects.equals(day, o.day)) return false;
      return start.isBefore(o.end) && o.start.isBefore(end);
    }
  }
}