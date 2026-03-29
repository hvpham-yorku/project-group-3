package com.yupathbuilder.backend.unit.controller;

import com.yupathbuilder.backend.scheduler_system.controller.TermsController;
import com.yupathbuilder.backend.scheduler_system.dto.TermDto;
import com.yupathbuilder.backend.scheduler_system.service.TermService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
    private TermService termService;

    @Test
    void listTermsMapsEntitiesToDtos() throws Exception {
        given(termService.listTerms()).willReturn(List.of(new TermDto(5L, "FALL", 2026)));

        mockMvc.perform(get("/api/terms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5))
                .andExpect(jsonPath("$[0].season").value("FALL"))
                .andExpect(jsonPath("$[0].year").value(2026));
    }
}
