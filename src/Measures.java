package com.cse416.tacos.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Measures {
    private Long id;
    private double populationEquality;
    private double compactness;
    private double enactedDeviation;
    private double averageDeviation;
    private double efficiencyGap;
    private double WeightOfPopulationEquality;
    private double WeightOfCompactness;
    private double WeightOfEnactedDeviation;
    private double WeightOfAverageDeviation;
    private ImprovementParams acceptThreshold;
    private double numOfMajorityMinority;


    public Measures() {
    }

    @OneToOne
    public ImprovementParams getAcceptThreshold() {
        return acceptThreshold;
    }

    public void setAcceptThreshold(ImprovementParams acceptThreshold) {
        this.acceptThreshold = acceptThreshold;
    }

    public double getNumOfMajorityMinority() {
        return numOfMajorityMinority;
    }

    public void setNumOfMajorityMinority(double numOfMajorityMinority) {
        this.numOfMajorityMinority = numOfMajorityMinority;
    }

    public Measures(Long id, double populationEquality, double compactness, double enactedDeviation, double averageDeviation, double weightOfPopulationEquality, double weightOfCompactness, double weightOfEnactedDeviation, double weightOfAverageDeviation, ImprovementParams acceptThreshold) {
        this.id = id;
        this.populationEquality = populationEquality;
        this.compactness = compactness;
        this.enactedDeviation = enactedDeviation;
        this.averageDeviation = averageDeviation;
        WeightOfPopulationEquality = weightOfPopulationEquality;
        WeightOfCompactness = weightOfCompactness;
        WeightOfEnactedDeviation = weightOfEnactedDeviation;
        WeightOfAverageDeviation = weightOfAverageDeviation;
        this.acceptThreshold = acceptThreshold;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public double getCompactness() {
        return compactness;
    }

    public void setCompactness(double compactness) {
        this.compactness = compactness;
    }

    public double getEnactedDeviation() {
        return enactedDeviation;
    }

    public void setEnactedDeviation(double enactedDeviation) {
        this.enactedDeviation = enactedDeviation;
    }

    public double getAverageDeviation() {
        return averageDeviation;
    }

    public void setAverageDeviation(double averageDeviation) {
        this.averageDeviation = averageDeviation;
    }

    public double getWeightOfPopulationEquality() {
        return WeightOfPopulationEquality;
    }

    public void setWeightOfPopulationEquality(double weightOfPopulationEquality) {
        WeightOfPopulationEquality = weightOfPopulationEquality;
    }

    public double getWeightOfCompactness() {
        return WeightOfCompactness;
    }

    public void setWeightOfCompactness(double weightOfCompactness) {
        WeightOfCompactness = weightOfCompactness;
    }

    public double getWeightOfEnactedDeviation() {
        return WeightOfEnactedDeviation;
    }

    public void setWeightOfEnactedDeviation(double weightOfEnactedDeviation) {
        WeightOfEnactedDeviation = weightOfEnactedDeviation;
    }

    public double getWeightOfAverageDeviation() {
        return WeightOfAverageDeviation;
    }

    public void setWeightOfAverageDeviation(double weightOfAverageDeviation) {
        WeightOfAverageDeviation = weightOfAverageDeviation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public double getEfficiencyGap() {
        return efficiencyGap;
    }

    public void setEfficiencyGap(double efficiencyGap) {
        this.efficiencyGap = efficiencyGap;
    }
}
