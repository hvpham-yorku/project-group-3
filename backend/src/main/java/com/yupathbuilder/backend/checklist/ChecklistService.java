package com.yupathbuilder.backend.checklist;

import com.yupathbuilder.backend.checklist.dto.ChecklistResponseDto;
import com.yupathbuilder.backend.repo.ProgramRequirementRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChecklistService {

    private final ProgramRequirementRepo repo;

    public ChecklistService(ProgramRequirementRepo repo) {
        this.repo = repo;
    }

    public ChecklistResponseDto byProgramId(Long programId) {
        var rows = repo.findChecklist(programId);

        Map<Integer, Map<String, List<ChecklistResponseDto.CourseDto>>> yearsMap = new LinkedHashMap<>();

        for (var pr : rows) {
            int year = pr.getYearLevel().intValue();            
            String groupName = (pr.getGroupName() == null || pr.getGroupName().isBlank()) ? "Core" : pr.getGroupName().trim();
            String reqType = pr.getReqType().name();
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
                String[] parts = g.getKey().split("\\|\\|", 2);
                groups.add(new ChecklistResponseDto.GroupDto(parts[0], parts[1], g.getValue()));
            }
            years.add(new ChecklistResponseDto.YearDto(y.getKey(), groups));
        }

        return new ChecklistResponseDto(programId, years);
    }
}