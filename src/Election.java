package com.cse416.tacos.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Election implements Serializable {
    private Long id;
    private String name;
    private PartyType party;
    private ElectionType electionType;
    private int numOfVotes;
    private int year;
    private boolean won;

    public Election(Long id, String name, PartyType party, ElectionType electionType, int numOfVotes, int year, boolean won) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.electionType = electionType;
        this.numOfVotes = numOfVotes;
        this.year = year;
        this.won = won;
    }

    public Election() {
    }

    @Id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PartyType getParty() {
        return party;
    }

    public void setParty(PartyType party) {
        this.party = party;
    }

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }

    public int getNumOfVotes() {
        return numOfVotes;
    }

    public void setNumOfVotes(int numOfVotes) {
        this.numOfVotes = numOfVotes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Id
    public Long getId() {
        return id;
    }

}
