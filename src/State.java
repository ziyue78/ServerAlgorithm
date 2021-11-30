package com.cse416.tacos.models;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;

@Entity
public class State {
    private Long id;
    private String name;
    private List<Population> populations;
    private List<Election> electionResults;
    private String geometry;
    private List<DistrictPlan> districtPlans;
    private List<BoxWhisker> BoxWhiskers;

    public State() {

    }

    @Column
    @OneToMany
    public List<BoxWhisker> getBoxWhiskers() {
        return BoxWhiskers;
    }

    public void setBoxWhiskers(List<BoxWhisker> boxWhiskers) {
        BoxWhiskers = boxWhiskers;
    }

    @Column
    @OneToMany
    public List<DistrictPlan> getDistrictPlans() {
        return districtPlans;
    }

    public void setDistrictPlans(List<DistrictPlan> districtPlans) {
        this.districtPlans = districtPlans;
    }

    public String getGeometry() {
        return geometry;
    }

    public State(Long id, String name, List<Population> populations, List<Election> electionResults, String geometry, List<DistrictPlan> districtPlans, List<BoxWhisker> boxWhiskers) {
        this.id = id;
        this.name = name;
        this.populations = populations;
        this.electionResults = electionResults;
        this.geometry = geometry;
        this.districtPlans = districtPlans;
        this.BoxWhiskers=boxWhiskers;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    @Column
    @OneToMany
    public List<Election> getElectionResults() {
        return electionResults;
    }

    public void setElectionResults(List<Election> electionResults) {
        this.electionResults = electionResults;
    }

    @Column
    @OneToMany
    public List<Population> getPopulations() {
        return populations;
    }

    public void setPopulations(List<Population> populations) {
        this.populations = populations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Population getPopulationByType(String populationType){
        switch (populationType){
            case "TOTAl":
                return populations.get(0);
            case "VAP":
                return populations.get(1);
            case "CVAP":
                return populations.get(2);
        }
        return populations.get(0);
    }

    @OneToOne
    public DistrictPlan getEnactedPlan(){
        for (DistrictPlan dp : this.districtPlans) {
            if (dp.isEnacted()) {
                return dp;
            }
        }
        return null;
    }

    public void setEnactedPlan(DistrictPlan dp){

    }

}
