package com.yupathbuilder.backend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="sections")
public class SectionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  @JoinColumn(name="course_id")
  private CourseEntity course;

  @ManyToOne(optional=false)
  @JoinColumn(name="term_id")
  private TermEntity term;

  @Column(name="section_code", nullable=false)
  private String sectionCode; // A, B, 01...

  @OneToMany(mappedBy="section", fetch=FetchType.LAZY)
  private List<SectionMeetingEntity> meetings;

  public Long getId() { return id; }

  public CourseEntity getCourse() { return course; }

  public TermEntity getTerm() { return term; }

  public String getSectionCode() { return sectionCode; }

  public List<SectionMeetingEntity> getMeetings() { return meetings; }
}