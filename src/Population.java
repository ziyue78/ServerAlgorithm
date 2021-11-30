package com.cse416.tacos.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Population {

    public int total;
    public int white;
    public int africanAmerican;
    public int americanIndian;
    public int asian;
    public int nativeHawaiian;
    public int other;
    public PopulationType populationType;
    private Long id;

    public Population() {
    }

    public PopulationType getPopulationType() {
        return populationType;
    }

    public void setPopulationType(PopulationType populationType) {
        this.populationType = populationType;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getAfricanAmerican() {
        return africanAmerican;
    }

    public void setAfricanAmerican(int africanAmerican) {
        this.africanAmerican = africanAmerican;
    }

    public int getAmericanIndian() {
        return americanIndian;
    }

    public void setAmericanIndian(int americanIndian) {
        this.americanIndian = americanIndian;
    }

    public int getAsian() {
        return asian;
    }

    public void setAsian(int asian) {
        this.asian = asian;
    }

    public int getNativeHawaiian() {
        return nativeHawaiian;
    }

    public void setNativeHawaiian(int nativeHawaiian) {
        this.nativeHawaiian = nativeHawaiian;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public Population(long id, int total, int white, int africanAmerican, int americanIndian, int asian,
                      int nativeHawaiian, int other, PopulationType type) {
        this.total = total;
        this.white = white;
        this.africanAmerican = africanAmerican;
        this.americanIndian = americanIndian;
        this.asian = asian;
        this.nativeHawaiian = nativeHawaiian;
        this.other = other;
        this.id=id;
        this.populationType=type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
