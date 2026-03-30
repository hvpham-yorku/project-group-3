package com.yupathbuilder.backend.scheduler_system.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * JPA entity representing a single meeting time within a section.
 */
@Entity
@Table(name="section_meetings")
public class SectionMeetingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  @JoinColumn(name="section_id")
  private SectionEntity section;

  @Column(name="day_of_week", nullable=false)
  private String dayOfWeek; // "MON", "TUE", ...

  @Column(name="start_time", nullable=false)
  private LocalTime startTime;

  @Column(name="end_time", nullable=false)
  private LocalTime endTime;

  @Column
  private String location;

  public Long getId() { return id; }

  public SectionEntity getSection() { return section; }

  public String getDayOfWeek() { return dayOfWeek; }

  public LocalTime getStartTime() { return startTime; }

  public LocalTime getEndTime() { return endTime; }

  public String getLocation() { return location; }
}
