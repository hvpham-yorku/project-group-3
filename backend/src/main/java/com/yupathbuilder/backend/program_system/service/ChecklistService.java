package com.yupathbuilder.backend.program_system.service;

import com.yupathbuilder.backend.program_system.dto.ChecklistResponseDto;
import com.yupathbuilder.backend.program_system.repo.ProgramRequirementRepo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Builds program checklists from persisted program requirement data.
 *
 * <p>This SQL-only service groups requirements into the nested DTO structure
 * expected by the frontend checklist view.</p>
 */
@Service
@Profile("!stub")
public class ChecklistService {

    private final ProgramRequirementRepo repo;

    public ChecklistService(ProgramRequirementRepo repo) {
        this.repo = repo;
    }

    /**
     * Returns the checklist for a program, grouped by year and requirement
     * category.
     */
    public ChecklistResponseDto byProgramId(Long programId) {
        var rows = repo.findChecklist(programId);

        Map<Integer, Map<String, List<ChecklistResponseDto.CourseDto>>> yearsMap = new LinkedHashMap<>();

        for (var pr : rows) {
            int year = pr.getYearLevel().intValue();
            String groupName = (pr.getGroupName() == null || pr.getGroupName().isBlank()) ? "Core" : pr.getGroupName().trim();
            String reqType = pr.getReqType().name();
            // The composite key preserves group identity while keeping insertion order stable.
            String groupKey = groupName + "||" + reqType;

            yearsMap.putIfAbsent(year, new LinkedHashMap<>());
            yearsMap.get(year).putIfAbsent(groupKey, new ArrayList<>());

            var c = pr.getCourse();
            yearsMap.get(year).get(groupKey).add(
                    new ChecklistResponseDto.CourseDto(c.getId(), c.getCourseCode(), c.getTitle())
            );
        }

        List<ChecklistResponseDto.YearDto> years = new ArrayList<>();
        for (var y : yearsMap.entrySet()) {
            List<ChecklistResponseDto.GroupDto> groups = new ArrayList<>();
            for (var g : y.getValue().entrySet()) {
                // Split the synthetic grouping key back into the API shape.
                String[] parts = g.getKey().split("\\|\\|", 2);
                groups.add(new ChecklistResponseDto.GroupDto(parts[0], parts[1], g.getValue()));
            }
            years.add(new ChecklistResponseDto.YearDto(y.getKey(), groups));
        }

        return new ChecklistResponseDto(programId, years);
    }
}

