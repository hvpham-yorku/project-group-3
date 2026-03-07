package com.yupathbuilder.backend.model;

public enum Season {
  FALL, WINTER, SUMMER;

  public static Season parse(String s) {
    if (s == null) throw new IllegalArgumentException("term season missing");
    return Season.valueOf(s.trim().toUpperCase());
  }
}