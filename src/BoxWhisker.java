package com.cse416.tacos.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BoxWhisker {
    private int id;
    private int stateId;
    private BasisType basisType;
    private double min;
    private double max;
    private double q1;
    private double q2;
    private double q3;
    // overlap可以自己算
    public BoxWhisker() {
    }

    public BoxWhisker(int id, int stateId, BasisType basisType, double min, double max, double q1, double q2, double q3) {
        this.id = id;
        this.stateId = stateId;
        this.basisType = basisType;
        this.min = min;
        this.max = max;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public BasisType getBasisType() {
        return basisType;
    }

    public void setBasisType(BasisType basisType) {
        this.basisType = basisType;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getQ1() {
        return q1;
    }

    public void setQ1(double q1) {
        this.q1 = q1;
    }

    public double getQ2() {
        return q2;
    }

    public void setQ2(double q2) {
        this.q2 = q2;
    }

    public double getQ3() {
        return q3;
    }

    public void setQ3(double q3) {
        this.q3 = q3;
    }

}
