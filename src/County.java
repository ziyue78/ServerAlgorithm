package com.cse416.tacos.models;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;

@Entity
public class County {
    private Long id;
    private String name;
    private Long stateId;
    private Population population;
    private List<Election> electionResult;
    private List<County> neighbors;
    private Geometry geometry;
    private boolean split;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public County() {
    }

    public County(Long id, String name, Long stateId, Population population, List<Election> electionResult, List<County> neighbors, Geometry geometry, boolean split) {
        this.id = id;
        this.name = name;
        this.stateId = stateId;
        this.population = population;
        this.electionResult = electionResult;
        this.neighbors = neighbors;
        this.geometry = geometry;
        this.split = split;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    @OneToOne
    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    @Column
    @OneToMany
    public List<Election> getElectionResult() {
        return electionResult;
    }

    public void setElectionResult(List<Election> electionResult) {
        this.electionResult = electionResult;
    }

    @Column
    @OneToMany
    public List<County> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<County> neighbors) {
        this.neighbors = neighbors;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }
}
