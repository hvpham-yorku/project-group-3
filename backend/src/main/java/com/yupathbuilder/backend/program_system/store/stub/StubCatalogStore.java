package com.yupathbuilder.backend.program_system.store.stub;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.program_system.dto.ChecklistResponseDto;
import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.store.CatalogStore;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

/**
 * Stub-backed implementation of the program catalog store.
 *
 * <p>This store loads faculties, programs, and checklist payloads from bundled
 * JSON resources so the application can run without database access.</p>
 */
@Component
@ConditionalOnProperty(name = "app.store", havingValue = "stub")
public class StubCatalogStore implements CatalogStore {

    private final List<FacultyEntity> faculties;
    private final List<ProgramEntity> programs;
    private final Map<Long, ChecklistResponseDto> checklistByProgram;

    public StubCatalogStore(ObjectMapper mapper) {
        this.faculties = loadFaculties(mapper);
        this.programs = loadPrograms(mapper, faculties);
        this.checklistByProgram = loadChecklists(mapper);
    }

    /**
     * Returns the in-memory faculty snapshot loaded at startup.
     */
    @Override
    public List<FacultyEntity> listFaculties() {
        return faculties;
    }

    /**
     * Returns all stub programs or those belonging to a selected faculty.
     */
    @Override
    public List<ProgramEntity> listPrograms(Long facultyId) {
        if (facultyId == null) return programs;
        return programs.stream()
                .filter(p -> p.getFaculty() != null && Objects.equals(p.getFaculty().getId(), facultyId))
                .toList();
    }

    /**
     * Returns the checklist for a program, falling back to an empty checklist
     * when stub data is missing for that program.
     */
    @Override
    public ChecklistResponseDto checklistByProgramId(Long programId) {
        ChecklistResponseDto dto = checklistByProgram.get(programId);
        if (dto == null) {
            // Return empty checklist if program not found
            return new ChecklistResponseDto(programId, List.of());
        }
        return dto;
    }

    /* -------------------- Loaders -------------------- */

    /**
     * Loads faculty records from stub JSON and adapts them into entity objects.
     */
    private static List<FacultyEntity> loadFaculties(ObjectMapper mapper) {
        List<FacultyJson> rows = readJson(mapper, "stub-data/faculties.json", new TypeReference<>() {});
        List<FacultyEntity> out = new ArrayList<>();

        for (FacultyJson f : rows) {
            FacultyEntity fe = new FacultyEntity();
            fe.setName(f.name());
            setIdReflective(fe, f.id());
            out.add(fe);
        }
        return Collections.unmodifiableList(out);
    }

    /**
     * Loads programs and reconnects them to the previously loaded faculty
     * entities.
     */
    private static List<ProgramEntity> loadPrograms(ObjectMapper mapper, List<FacultyEntity> faculties) {
        Map<Long, FacultyEntity> facultyMap = new HashMap<>();
        for (FacultyEntity f : faculties) facultyMap.put(f.getId(), f);

        List<ProgramJson> rows = readJson(mapper, "stub-data/programs.json", new TypeReference<>() {});
        List<ProgramEntity> out = new ArrayList<>();

        for (ProgramJson p : rows) {
            ProgramEntity pe = new ProgramEntity();
            pe.setName(p.name());
            pe.setDegree(p.degree());
            pe.setFaculty(facultyMap.get(p.facultyId()));
            setIdReflective(pe, p.id());
            out.add(pe);
        }
        return Collections.unmodifiableList(out);
    }

    /**
     * Loads checklist payloads that are already shaped close to the API DTO.
     */
    private static Map<Long, ChecklistResponseDto> loadChecklists(ObjectMapper mapper) {
        List<ChecklistJson> rows = readJson(mapper, "stub-data/checklists.json", new TypeReference<>() {});
        Map<Long, ChecklistResponseDto> out = new HashMap<>();

        for (ChecklistJson c : rows) {
            // We already store the JSON in the same shape as ChecklistResponseDto (almost),
            // so convert it directly.
            ChecklistResponseDto dto = new ChecklistResponseDto(
                    c.programId(),
                    c.years()
            );
            out.put(c.programId(), dto);
        }

        return Collections.unmodifiableMap(out);
    }

    /**
     * Reads a stub JSON resource into the requested target type.
     */
    private static <T> T readJson(ObjectMapper mapper, String path, TypeReference<T> type) {
        try {
            ClassPathResource res = new ClassPathResource(path);
            try (InputStream in = res.getInputStream()) {
                return mapper.readValue(in, type);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load stub data: " + path + " (" + e.getMessage() + ")", e);
        }
    }

    // helper to set @Id without changing entity code
    /**
     * Assigns entity IDs reflectively so stub data can behave like persisted
     * entities without changing the entity classes.
     */
    private static void setIdReflective(Object entity, Long id) {
        try {
            var f = entity.getClass().getDeclaredField("id");
            f.setAccessible(true);
            f.set(entity, id);
        } catch (Exception ignored) {}
    }

    /* -------------------- JSON record models -------------------- */

    /**
     * JSON shape for stub faculty records.
     */
    private record FacultyJson(Long id, String name) {}

    /**
     * JSON shape for stub program records.
     */
    private record ProgramJson(Long id, Long facultyId, String name, String degree) {}

    /**
     * JSON shape for stub checklist records.
     */
    private record ChecklistJson(
            Long programId,
            List<ChecklistResponseDto.YearDto> years
    ) {}
}

