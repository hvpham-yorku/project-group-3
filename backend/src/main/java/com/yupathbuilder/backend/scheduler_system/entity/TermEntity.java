package com.yupathbuilder.backend.scheduler_system.entity;

import com.yupathbuilder.backend.scheduler_system.model.Season;
import jakarta.persistence.*;

/**
 * JPA entity representing an academic term.
 */
@Entity
@Table(
    name = "terms",
    uniqueConstraints = @UniqueConstraint(columnNames = {"season","year"})
)
public class TermEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private Season season;

  @Column(nullable=false)
  private int year;

  public Long getId() { return id; }

  public Season getSeason() { return season; }
  public void setSeason(Season s) { this.season = s; }

  public int getYear() { return year; }
  public void setYear(int y) { this.year = y; }
}

