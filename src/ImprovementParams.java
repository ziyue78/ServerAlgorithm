package com.cse416.tacos.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ImprovementParams {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private double populationEquality;
    private double compactness;
    private double enactedDeviation;
    private double averageDeviation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }


    public double getEnactedDeviation() {
        return enactedDeviation;
    }

    public void setEnactedDeviation(double enactedDeviation) {
        this.enactedDeviation = enactedDeviation;
    }

    public double getCompactness() {
        return compactness;
    }

    public void setCompactness(double compactness) {
        this.compactness = compactness;
    }
}
