package com.yupathbuilder.backend.util;

import com.yupathbuilder.backend.model.Season;

public final class TermParser {
  private TermParser() {}

  public record TermKey(Season season, int year) {}

  public static TermKey parse(String term) {
    // accepts "FALL 2026"
    if (term == null) throw new IllegalArgumentException("term is required");
    String[] parts = term.trim().split("\\s+");
    if (parts.length != 2) throw new IllegalArgumentException("term must be like 'FALL 2026'");
    Season season = Season.parse(parts[0]);
    int year = Integer.parseInt(parts[1]);
    return new TermKey(season, year);
  }
}