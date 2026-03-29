package com.yupathbuilder.backend.course_catalog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class CourseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="course_code", nullable=false, unique=true)
  private String courseCode;

  @Column(nullable=false)
  private String title;

  @Column
  private String subject;

  @Column(name="catalog_number")
  private String catalogNumber;

  @Column(columnDefinition = "TEXT")
  private String description;

  public Long getId() { return id; }

  public String getCourseCode() { return courseCode; }
  public void setCourseCode(String v) { this.courseCode = v; }

  public String getTitle() { return title; }
  public void setTitle(String v) { this.title = v; }

  public String getSubject() { return subject; }
  public void setSubject(String v) { this.subject = v; }

  public String getCatalogNumber() { return catalogNumber; }
  public void setCatalogNumber(String v) { this.catalogNumber = v; }

  public String getDescription() { return description; }
  public void setDescription(String v) { this.description = v; }
}
