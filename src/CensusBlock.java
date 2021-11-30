package com.cse416.tacos.models;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;

@Entity
public class CensusBlock {

    private Long id;
    private String name;
    private Long stateId;
    private Long districtId;
    private Long PrecinctId;
    private Population population;
    private List<Election> electionResult;
    private List<CensusBlock> neighbors;
    private Geometry geometry;
    private boolean border;

    public CensusBlock(Long id, String name, Long stateId, Long districtId, Long precinctId, Population population, List<Election> electionResult, List<CensusBlock> neighbors, Geometry geometry, boolean border) {
        this.id = id;
        this.name = name;
        this.stateId = stateId;
        this.districtId = districtId;
        PrecinctId = precinctId;
        this.population = population;
        this.electionResult = electionResult;
        this.neighbors = neighbors;
        this.geometry = geometry;
        this.border = border;
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

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getPrecinctId() {
        return PrecinctId;
    }

    public void setPrecinctId(Long precinctId) {
        PrecinctId = precinctId;
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
    public List<CensusBlock> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<CensusBlock> neighbors) {
        this.neighbors = neighbors;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public CensusBlock() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
