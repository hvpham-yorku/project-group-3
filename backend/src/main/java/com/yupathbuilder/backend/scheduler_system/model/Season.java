package com.yupathbuilder.backend.scheduler_system.model;

/**
 * Enumerates academic seasons recognized by the scheduling subsystem.
 */
public enum Season {
  FALL, WINTER, SUMMER;

  /**
   * Parses a season name in a case-insensitive way.
   */
  public static Season parse(String s) {
    if (s == null) throw new IllegalArgumentException("term season missing");
    return Season.valueOf(s.trim().toUpperCase());
  }
}
