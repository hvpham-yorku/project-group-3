package com.yupathbuilder.backend.scheduler_system.dto;

/**
 * Transport representation of an academic term.
 *
 * @param id persistent term identifier
 * @param season academic season name
 * @param year academic year
 */
public record TermDto(Long id, String season, int year) {}
