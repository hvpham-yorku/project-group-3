package com.yupathbuilder.backend.unit.service;

import com.yupathbuilder.backend.checklist.ChecklistService;
import com.yupathbuilder.backend.entity.CourseEntity;
import com.yupathbuilder.backend.entity.ProgramRequirementEntity;
import com.yupathbuilder.backend.repo.ProgramRequirementRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ChecklistServiceUnitTest {

    @Mock
    private ProgramRequirementRepo repo;

    @InjectMocks
    private ChecklistService service;

    @Test
    void byProgramIdGroupsCoursesByYearAndRequirementGroup() throws Exception {
        given(repo.findChecklist(1L)).willReturn(List.of(
                requirement(1, "Core", ProgramRequirementEntity.ReqType.REQUIRED, course(1011L, "EECS 1011", "Computational Thinking")),
                requirement(1, "Core", ProgramRequirementEntity.ReqType.REQUIRED, course(1013L, "MATH 1013", "Applied Calculus I")),
                requirement(2, "Security", ProgramRequirementEntity.ReqType.ELECTIVE, course(3482L, "EECS 3482", "Intro to Computer Security"))
        ));

        var dto = service.byProgramId(1L);

        assertEquals(1L, dto.programId());
        assertEquals(2, dto.years().size());
        assertEquals(1, dto.years().get(0).year());
        assertEquals("Core", dto.years().get(0).groups().get(0).groupName());
        assertEquals(2, dto.years().get(0).groups().get(0).courses().size());
        assertEquals("Security", dto.years().get(1).groups().get(0).groupName());
        assertEquals("ELECTIVE", dto.years().get(1).groups().get(0).reqType());
    }

    @Test
    void byProgramIdUsesCoreWhenGroupNameBlank() throws Exception {
        given(repo.findChecklist(7L)).willReturn(List.of(
                requirement(1, "   ", ProgramRequirementEntity.ReqType.REQUIRED, course(2311L, "EECS 2311", "Software Development Project"))
        ));

        var dto = service.byProgramId(7L);

        assertEquals("Core", dto.years().get(0).groups().get(0).groupName());
    }

    private static ProgramRequirementEntity requirement(int year, String groupName, ProgramRequirementEntity.ReqType type, CourseEntity course) throws Exception {
        ProgramRequirementEntity entity = new ProgramRequirementEntity();
        setField(entity, "yearLevel", (byte) year);
        setField(entity, "groupName", groupName);
        setField(entity, "reqType", type);
        setField(entity, "course", course);
        return entity;
    }

    private static CourseEntity course(Long id, String code, String title) throws Exception {
        CourseEntity c = new CourseEntity();
        setField(c, "id", id);
        c.setCourseCode(code);
        c.setTitle(title);
        return c;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
