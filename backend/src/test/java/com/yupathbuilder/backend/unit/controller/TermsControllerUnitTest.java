package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.controller.TermsController;
import com.yupathbuilder.backend.entity.TermEntity;
import com.yupathbuilder.backend.model.Season;
import com.yupathbuilder.backend.repo.TermRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TermsController.class)
@AutoConfigureMockMvc(addFilters = false)
@Disabled("Temporarily disabled due to WebMvcTest ApplicationContext issue under current Boot 4 test setup")
class TermsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TermRepo termRepo;

    @Test
    void listTermsMapsEntitiesToDtos() throws Exception {
        TermEntity term = new TermEntity();
        term.setSeason(Season.FALL);
        term.setYear(2026);
        setId(term, 5L);

        given(termRepo.findAll()).willReturn(List.of(term));

        mockMvc.perform(get("/api/terms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5))
                .andExpect(jsonPath("$[0].season").value("FALL"))
                .andExpect(jsonPath("$[0].year").value(2026));
    }

    private static void setId(Object target, Long value) throws Exception {
        Field field = target.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(target, value);
    }
}