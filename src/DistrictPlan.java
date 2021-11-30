package com.cse416.tacos.models;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.*;

@Entity
public class DistrictPlan {
    private Long id;
    private String name;
    private int stateId;
    private boolean isEnacted;
    private Measures measure;
    private int numOfMajorityMinority;
//    private Map<Integer, Integer> numOfCensusBlockInDistrict;
    private String previewImage;
    /** #####
     * totNumIteration: int
     */
    private List<District> districts;

    public DistrictPlan(Long id, String name, boolean isEnacted, List<District> districts) {
        this.id = id;
        this.name = name;
        this.isEnacted = isEnacted;
        this.districts = districts;
    }

    public DistrictPlan(Long id, boolean isEnacted, String name) {
        this.id=id;
        this.isEnacted = isEnacted;
        this.name=name;
    }

    public DistrictPlan() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    @OneToOne
    public Measures getMeasure() {
        return measure;
    }

    public void setMeasure(Measures measure) {
        this.measure = measure;
    }

    public int getNumOfMajorityMinority() {
        return numOfMajorityMinority;
    }

    public void setNumOfMajorityMinority(int numOfMajorityMinority) {
        this.numOfMajorityMinority = numOfMajorityMinority;
    }

    // #####
    //
    // TODO: ADD annotation, need to think about it
//    @OneToMany(mappedBy = "DistrictPlan")
//    @MapKey(name="Id")
//     public Map<Integer, Integer> getNumOfCensusBlockInDistrict() {
//        return numOfCensusBlockInDistrict;
//     }

//    public void setNumOfCensusBlockInDistrict(Map<Integer, Integer> numOfCensusBlockInDistrict) {
//        this.numOfCensusBlockInDistrict = numOfCensusBlockInDistrict;
//    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    @Column
    @OneToMany
    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }


    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnacted() {
        return isEnacted;
    }

    public void setEnacted(boolean enacted) {
        isEnacted = enacted;
    }



    public District findMostPopulous(){
        int maxNum = districts.get(0).getPopulation().getTotal();
        int maxIndex = 0;
        for(int j = 0; j < districts.size(); j++){//every district
            int tempTotalPopulation = districts.get(j).getPopulation().getTotal();
            if(maxNum < tempTotalPopulation){
                maxNum = tempTotalPopulation;
                maxIndex = j;

            }
        }
        return districts.get(maxIndex);
    }
    public double calPopulationEquality(){
        int totalPopulation = 0;

        int maxNum = districts.get(0).getPopulation().getTotal();
        int minNum = districts.get(0).getPopulation().getTotal();
        for (int j = 0; j < districts.size(); j++){
            int tempTotalPopulation = districts.get(j).getPopulation().getTotal();
            totalPopulation += tempTotalPopulation;
            if(maxNum < tempTotalPopulation){
                maxNum = tempTotalPopulation;
            }
            if(minNum > tempTotalPopulation){
                minNum = tempTotalPopulation;
            }
        }
        return (maxNum - minNum) / (totalPopulation / districts.size());
    }

    public double calEnactedDeviation(DistrictPlan enactedPlan){
        List<District> enactedDistricts = enactedPlan.getDistricts();
        List<Integer> enactedPop = new ArrayList<Integer>();
        List<Integer> newPop = new ArrayList<Integer>();
        for(int j = 0; j < enactedDistricts.size(); j++){
            enactedPop.add(enactedDistricts.get(j).getPopulation().getTotal());
            newPop.add(districts.get(j).getPopulation().getTotal());
        }
        Collections.sort(enactedPop);
        Collections.sort(newPop);
        double deviation = 0;
        for (int j = 0; j < enactedPop.size(); j++){
            deviation += Math.pow(enactedPop.get(j) - newPop.get(j), 2);
        }
        deviation /= enactedPop.size();
        return Math.sqrt(deviation);
    }

    public int calPlanTotalVotes(){
        int total = 0;
        for (int i = 0; i < districts.size(); i++){
            total += districts.get(i).totalDistrictVotes();
        }
        return total;
    }
    public double calEfficiencyGap(){
        int dVotesWasted = 0;
        int rVotesWasted = 0;
        int totalPlanVotes = calPlanTotalVotes();
        for (int j = 0; j < districts.size(); j++){
            int totalDistrictVotes = districts.get(j).totalDistrictVotes();
            int dVotes = 0;
            int rVotes = 0;
            for(int k = 0; k < districts.get(j).getElectionResult().size(); k++) {
                if (districts.get(j).getElectionResult().get(k).getParty() == (PartyType.DEMOCRATIC)) {
                    dVotes = districts.get(j).getElectionResult().get(k).getNumOfVotes();
                } else if (districts.get(j).getElectionResult().get(k).getParty() == (PartyType.REPUBLICAN)) {
                    rVotes = districts.get(j).getElectionResult().get(k).getNumOfVotes();
                }
            }
            double votesToWin = Math.ceil(totalDistrictVotes/2.0);
            if (dVotes > rVotes){
                dVotesWasted += dVotes - votesToWin;
                rVotesWasted += rVotes;
            }
            else if (rVotes > dVotes){
                rVotesWasted += rVotes - votesToWin;
                dVotesWasted += dVotes;
            }
        }
        double newChange = 0;
        if(dVotesWasted > rVotesWasted){
            newChange = (dVotesWasted - rVotesWasted)/totalPlanVotes;
        }
        else if(rVotesWasted > dVotesWasted){
            newChange = (rVotesWasted - dVotesWasted)/totalPlanVotes;
        }
        return newChange;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
