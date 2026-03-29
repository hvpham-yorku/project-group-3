package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.program_system.controller.ProgramController;
import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.service.ProgramCatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgramController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProgramControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProgramCatalogService programCatalogService;

    @Test
    void facultiesReturnsCatalogStoreData() throws Exception {
        FacultyEntity faculty = new FacultyEntity();
        faculty.setName("Engineering");
        setId(faculty, 1L);
        given(programCatalogService.listFaculties()).willReturn(List.of(faculty));

        mockMvc.perform(get("/api/faculties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Engineering"));
    }

    @Test
    void programsPassesFacultyIdFilterThrough() throws Exception {
        ProgramEntity program = new ProgramEntity();
        program.setName("Software Engineering (Security Stream)");
        program.setDegree("BEng");
        given(programCatalogService.listPrograms(1L)).willReturn(List.of(program));

        mockMvc.perform(get("/api/programs").param("facultyId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Software Engineering (Security Stream)"));

        verify(programCatalogService).listPrograms(1L);
    }

    private static void setId(Object target, Long value) throws Exception {
        Field field = target.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(target, value);
    }
}

