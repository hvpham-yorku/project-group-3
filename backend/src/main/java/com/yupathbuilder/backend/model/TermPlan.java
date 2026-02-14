package com.yupathbuilder.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Domain model: TermPlan.
 *
 * <p>Simple immutable data structures used throughout the backend.
 */

public class TermPlan {
    private String id;
    private String name;
    private Term term;
    private List<Section> selectedSections = new ArrayList<>();

    public TermPlan() {}

    public TermPlan(String name, Term term) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.term = term;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Term getTerm() { return term; }
    public void setTerm(Term term) { this.term = term; }

    public List<Section> getSelectedSections() { return selectedSections; }
    public void setSelectedSections(List<Section> selectedSections) { this.selectedSections = selectedSections; }
}
