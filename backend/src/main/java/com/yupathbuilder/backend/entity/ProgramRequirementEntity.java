package com.yupathbuilder.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "program_requirements")
public class ProgramRequirementEntity {

    public enum ReqType { REQUIRED, ELECTIVE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private ProgramEntity program;

    @Column(name = "year_level", nullable = false)
    private Byte yearLevel;  // maps cleanly to MySQL TINYINT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Enumerated(EnumType.STRING)
    @Column(name = "req_type", nullable = false)
    private ReqType reqType;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    public Long getId() { return id; }
    public ProgramEntity getProgram() { return program; }
    public Byte getYearLevel() { return yearLevel; }
    public CourseEntity getCourse() { return course; }
    public ReqType getReqType() { return reqType; }
    public String getGroupName() { return groupName; }
    public int getDisplayOrder() { return displayOrder; }
}