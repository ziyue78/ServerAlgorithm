package com.cse416.tacos.models;

//import org.opengis.geometry.Geometry;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
public class District {
    private Long id;
    private String name;
    private Population population;
    private List<Election> electionResult;
    private List<District> neighbors;
    private Geometry geometry;
    private boolean majorityMinority;
    private List<Precinct> borderPrecincts;
    private List<CensusBlock> removed;
    private List<CensusBlock> added;
    private List<CensusBlock> censusBlocks;
    private List<Precinct> precincts;

    public District(Long id, String name, String geometry) {
        this.id = id;
        this.name = name;
        this.geometry = geometry;
    }

    public District(Long id, String name, Population population, List<Election> electionResult, List<District> neighbors,
                    String geometry, boolean majorityMinority, List<Precinct> borderPrecincts,
                    List<CensusBlock> removed, List<CensusBlock> added, List<CensusBlock> censusBlocks,
                    List<Precinct> precincts){
        this.id=id;
        this.name=name;
        this.population=population;
        this.electionResult=electionResult;
        this.neighbors=neighbors;
        this.geometry=geometry;
        this.majorityMinority=majorityMinority;
        this.borderPrecincts=borderPrecincts;
        this.removed=removed;
        this.added=added;
        this.censusBlocks=censusBlocks;
        this.precincts=precincts;
    }

    public District() {
    }
    public String getName() {return name;}

    @OneToOne
    public Population getPopulation() {return population;}

    @Column
    @OneToMany
    public List<Election> getElectionResult() {return electionResult;}

    @Column
    @OneToMany
    public List<District> getNeighbors() {return neighbors;}
    public Geometry getGeometry() {return geometry;}

    @Column
    @OneToMany
    public List<Precinct> getBorderPrecincts() {return  borderPrecincts;}

    @Column
    @OneToMany
    public List<CensusBlock> getRemoved() {return removed;}

    @Column
    @OneToMany
    public List<CensusBlock> getAdded() {return added;}

    @Column
    @OneToMany
    public List<CensusBlock> getCensusBlocks() {
        return censusBlocks;
    }

    @Column
    @OneToMany
    public List<Precinct> getPrecincts() {
        return precincts;
    }

    public boolean isMajorityMinority() {
        return majorityMinority;
    }

    public void setPrecincts(List<Precinct> precincts) {
        this.precincts = precincts;
    }

    public void setCensusBlocks(List<CensusBlock> censusBlocks) {
        this.censusBlocks = censusBlocks;
    }

    public void setAdded(List<CensusBlock> added) {
        this.added = added;
    }

    public void setRemoved(List<CensusBlock> removed) {
        this.removed = removed;
    }

    public void setBorderPrecincts(List<Precinct> borderPrecincts) {
        this.borderPrecincts = borderPrecincts;
    }

    public void setMajorityMinority(boolean majorityMinority) {
        this.majorityMinority = majorityMinority;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public void setNeighbors(List<District> neighbors) {
        this.neighbors = neighbors;
    }

    public void setElectionResult(List<Election> electionResult) {
        this.electionResult = electionResult;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CensusBlock getRandomCensusBlock(List<CensusBlock> CBs){
        Random rand = new Random();
        return CBs.get(rand.nextInt(CBs.size()));
    }
    public void addCensusBlock(CensusBlock cb){
        censusBlocks.add(cb);
        int total = population.getTotal() + addedCensusBlock.getPopulation().getTotal();
        population.setTotal(total);
        int white = population.getWhite() + addedCensusBlock.getPopulation().getWhite();
        population.setWhite(white);
        int aA = population.getAfricanAmerican() + addedCensusBlock.getPopulation().getAfricanAmerican();
        population.setAfricanAmerican(aA);
        int aI = population.getAmericanIndian() + addedCensusBlock.getPopulation().getAmericanIndian();
        population.setAmericanIndian(aI);
        int asian = population.getAsian() + addedCensusBlock.getPopulation().getAsian();
        population.setAsian(asian);
        int nativeHawaiian = population.getNativeHawaiian() + addedCensusBlock.getPopulation().getNativeHawaiian();
        population.setNativeHawaiian(nativeHawaiian);
        int other = population.getOther() + addedCensusBlock.getPopulation().getOther();
        population.setOther(other);

        int democraticCensusBlock = 0;
        int republicanCensusBlock = 0;
        int otherCensusBlock = 0;
        for(int i = 0; i < addedCensusBlock.getElectionResult().size(); i++){
            if (addedCensusBlock.getElectionResult().get(i).getParty() == (PartyType.DEMOCRATIC)) {
                democraticCensusBlock = addedCensusBlock.getElectionResult().get(i).getNumOfVotes();
            }
            else if (addedCensusBlock.getElectionResult().get(i).getParty() == (PartyType.REPUBLICAN)) {
                republicanCensusBlock = addedCensusBlock.getElectionResult().get(i).getNumOfVotes();
            }
            else if (addedCensusBlock.getElectionResult().get(i).getParty() == (PartyType.OTHER)) {
                otherCensusBlock = addedCensusBlock.getElectionResult().get(i).getNumOfVotes();
            }
        }
        //update election result
        for (int i = 0; i < electionResult.size(); i++){
            if (electionResult.get(i).getParty() == (PartyType.DEMOCRATIC)) {
                electionResult.get(i).setNumOfVotes(electionResult.get(i).getNumOfVotes() + democraticCensusBlock);
            }
            else if (electionResult.get(i).getParty() == (PartyType.REPUBLICAN)) {
                electionResult.get(i).setNumOfVotes(electionResult.get(i).getNumOfVotes() + republicanCensusBlock);
            }
            else if (electionResult.get(i).getParty() == (PartyType.OTHER)) {
                electionResult.get(i).setNumOfVotes(electionResult.get(i).getNumOfVotes() + otherCensusBlock);
            }
        }
    }

    public void removeCensusBlock(CensusBlock cb){
        censusBlocks.removeIf(item -> item.equals(cb));
        removed.remove(new CensusBlock(cb));
        int total = population.getTotal() - cb.getPopulation().getTotal();
        population.setTotal(total);
        int white = population.getWhite() - cb.getPopulation().getWhite();
        population.setWhite(white);
        int aA = population.getAfricanAmerican() - cb.getPopulation().getAfricanAmerican();
        population.setAfricanAmerican(aA);
        int aI = population.getAmericanIndian() - cb.getPopulation().getAmericanIndian();
        population.setAmericanIndian(aI);
        int asian = population.getAsian() - cb.getPopulation().getAsian();
        population.setAsian(asian);
        int nativeHawaiian = population.getNativeHawaiian() - cb.getPopulation().getNativeHawaiian();
        population.setNativeHawaiian(nativeHawaiian);
        int other = population.getOther() - cb.getPopulation().getOther();
        population.setOther(other);

        int democraticCensusBlock = 0;
        int republicanCensusBlock = 0;
        int otherCensusBlock = 0;
        for(int i = 0; i < cb.getElectionResult().size(); i++){
            if (cb.getElectionResult().get(i).getParty() == (PartyType.DEMOCRATIC)) {
                democraticCensusBlock = cb.getElectionResult().get(i).getNumOfVotes();
            }
            else if (cb.getElectionResult().get(i).getParty() == (PartyType.REPUBLICAN)) {
                republicanCensusBlock = cb.getElectionResult().get(i).getNumOfVotes();
            }
            else if (cb.getElectionResult().get(i).getParty() == (PartyType.OTHER)) {
                otherCensusBlock = cb.getElectionResult().get(i).getNumOfVotes();
            }
        }
        //update election result
        for (int i = 0; i < electionResult.size(); i++){
            if (electionResult.get(i).getParty() == (PartyType.DEMOCRATIC)) {
                electionResult.get(i).setNumOfVotes(electionResult.get(i).getNumOfVotes() - democraticCensusBlock);
            }
            else if (electionResult.get(i).getParty() == (PartyType.REPUBLICAN)) {
                electionResult.get(i).setNumOfVotes(electionResult.get(i).getNumOfVotes() - republicanCensusBlock);
            }
            else if (electionResult.get(i).getParty() == (PartyType.OTHER)) {
                electionResult.get(i).setNumOfVotes(electionResult.get(i).getNumOfVotes() - otherCensusBlock);
            }
        }
    }

    public int findMostPopulousDistrict(List<District> districts){
        //是不是该放district controller
        return -1;
    }

    public List<District> updateDistricts(List<District> districts){
        return null;
    }

    public void calAllDemographic(CensusBlock censusBlock, List<District> districts){

    }

    public void updateBorder(){

    }

    public List<CensusBlock> CBsInDistrict(List<CensusBlock> censusBlocks, District distirct){
        return null;
        // district 那里在class diagram上写的是congressionalDistrict，
    }

    public void changeDistrict(List<CensusBlock> censusBlocks){

    }

    public void getUnionOfRemovedCB(){

    }

    public List<CensusBlock> findCensusBlocksInDistrict(District district,int CBid){
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }




    public Precinct randomBorderPrecinct(){//TODO: add by Ziyue
        Random rand = new Random();
        int upperbound = borderPrecincts.size();
        int randomNum = rand.nextInt(upperbound);
        return borderPrecincts.get(randomNum);
    }
    public int totalDistrictVotes(){
        int total = 0;
        for(int i = 0; i < electionResult.size(); i++){
            total += electionResult.get(i).getNumOfVotes();
        }
        return total;
    }


}











