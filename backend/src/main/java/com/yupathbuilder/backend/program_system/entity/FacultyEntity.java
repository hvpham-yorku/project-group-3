package com.yupathbuilder.backend.program_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "faculties")
public class FacultyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String name;

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
