package com.yupathbuilder.backend.util;

import com.yupathbuilder.backend.scheduler_system.model.Season;

/**
 * Utility for converting user-facing term strings into structured term keys.
 *
 * <p>This helper is shared by scheduling-related services so term parsing rules
 * stay consistent across endpoints and persistence lookups.</p>
 */
public final class TermParser {
  private TermParser() {}

  /**
   * Structured representation of a parsed academic term.
   */
  public record TermKey(Season season, int year) {}

  /**
   * Parses terms in the canonical format {@code SEASON YEAR}, for example
   * {@code FALL 2026}.
   */
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
