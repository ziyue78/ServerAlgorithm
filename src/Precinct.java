package com.cse416.tacos.models;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
public class Precinct {
    private Long id;
    private String name;
    private Long districtId;
    private Long stateId;
    private Population population;
    private List<Election> electionResult;
    private List<Precinct> neighbors;
    private Geometry geometry;
    private boolean split;
    private boolean border;
    private List<CensusBlock> borderBlocks;

    public Precinct() {
    }

    public Precinct(Long id, String name, Long districtId, Long stateId, Population population, List<Election> electionResult, List<Precinct> neighbors, Geometry geometry, boolean split, boolean border, List<CensusBlock> censusBlocks) {
        this.id = id;
        this.name = name;
        this.districtId = districtId;
        this.stateId = stateId;
        this.population = population;
        this.electionResult = electionResult;
        this.neighbors = neighbors;
        this.geometry = geometry;
        this.split = split;
        this.border = border;
        this.censusBlocks = censusBlocks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
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
    public List<Precinct> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Precinct> neighbors) {
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

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    @Column
    @OneToMany
    public List<CensusBlock> getCensusBlocks() {
        return censusBlocks;
    }

    public void setCensusBlocks(List<CensusBlock> censusBlocks) {
        this.censusBlocks = censusBlocks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }


    public CensusBlock randomBorderCB(){//TODO: add by Ziyue
        Random rand = new Random();
        int upperbound = borderBlocks.size();
        int randomNum = rand.nextInt(upperbound);
        return borderBlocks.get(randomNum);
    }



}
