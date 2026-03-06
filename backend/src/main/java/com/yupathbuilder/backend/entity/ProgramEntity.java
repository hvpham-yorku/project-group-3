package com.yupathbuilder.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "programs")
public class ProgramEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private FacultyEntity faculty;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(length = 60)
    private String degree;

    public Long getId() { return id; }
    public FacultyEntity getFaculty() { return faculty; }
    public void setFaculty(FacultyEntity faculty) { this.faculty = faculty; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }
}