package com.cse416.tacos.models;
import com.cse416.tacos.database.DistrictPlanRepo;
import org.locationtech.jts.geom.Geometry;
import java.util.*;
public class Server_Algorithm {
    MAX_ITER;
    private int MAX_ATTEMPTS = 50;
    private double Temperature = 0.05;
    public void moveCensusBlock(int planId){
        // 1. get districting plan
        DistrictPlanRepo Districting = new DistrictPlanRepo();
        DistrictPlan NewDistrictPlan = new DistrictPlan();
        NewDistrictPlan = Districting.findPlanById(planId);


        DistrictPlan NewDistrictPlanCopy = NewDistrictPlan.clone();
        //2. due to the districting plan, we can have a list of district called districts
        //3. based on every district, we find border precincts
        List<List<Precinct>> borderPrecincts = new ArrayList<List<Precinct>>();
        List<District> districts = Districting.getDistricts();


        //4. in the loop


        int attempts = 0;
        boolean negativeMove = false;
        for(int i = 0; i < MAX_ITER; i++){
            //this step is for finding the most populous district
            District maxDistrict = NewDistrictPlan.findMostPopulous();
            Precinct candidatePrecinct = maxDistrict.randomBorderPrecinct();
            CensusBlock candidateCensusBlock = candidatePrecinct.randomBorderCB();

            //to find which district census block is connected
            List<CensusBlock> neighCensusBlock = candidateCensusBlock.getNeighbors();
            long districtIdMoveTo = 0;
            District districtMoveTo = new District();
            for (int j = 0; j < neighCensusBlock.size(); j++){
                districtIdMoveTo = neighCensusBlock.get(j).getDistrictId();
                if(districtIdMoveTo != candidateCensusBlock.getDistrictId()){
                    //since there are two different districts, then move candidate
                    for(int k = 0; k < districts.size(); k++){
                        if(districts.get(k).getId() == districtIdMoveTo){
                            districtMoveTo = districts.get(k);
                            districtMoveTo.addCensusBlock(candidateCensusBlock);
                            maxDistrict.removeCensusBlock(candidateCensusBlock);
                            break;
                        }
                    }
                    break;
                }
            }

            //4.5. set the districts

            //5. calculate the measure
            //1> population equality

            double variantPercentage = NewDistrictPlan.calPopulationEquality();

            double equalityThreshold = NewDistrictPlan.getMeasure().getAcceptThreshold().getPopulationEquality();
            boolean acceptProbability = calculateAcceptPro(variantPercentage, NewDistrictPlan.getMeasure().getPopulationEquality());
            if(variantPercentage < equalityThreshold){
                if(acceptProbability == true) {// within the probability, then accept it
                    NewDistrictPlan.getMeasure().setPopulationEquality(variantPercentage);
                }
                else {
                    attempts += 1;
                    NewDistrictPlan = NewDistrictPlanCopy;
                    continue;
                }
            }
            else {//violate the constraints
                attempts += 1;
                NewDistrictPlan = NewDistrictPlanCopy;
                continue;
            }

            //2> enactedDeviation
            //population measure

            State enactedState = new State();//TODO
            DistrictPlan enactedPlan = enactedState.getEnactedPlan();
            double deviation = NewDistrictPlan.calEnactedDeviation(enactedPlan);
            acceptProbability = calculateAcceptPro(deviation,NewDistrictPlan.getMeasure().getEnactedDeviation());
            double enactedDevThreshold = NewDistrictPlan.getMeasure().getAcceptThreshold().getEnactedDeviation();
            if(deviation < enactedDevThreshold){// next
                if(acceptProbability == true) {// within the probability, then accept it
                    NewDistrictPlan.getMeasure().setEnactedDeviation(deviation);
                }
                else {
                    attempts += 1;
                    NewDistrictPlan = NewDistrictPlanCopy;
                    continue;
                }
            }
            else {//violate the constraints
                attempts += 1;
                NewDistrictPlan = NewDistrictPlanCopy;
                continue;
            }


            //3> efficiency gap is for political fairness
            // (max - min)/total vote

            double efficiencyGap = NewDistrictPlan.calEfficiencyGap();
            acceptProbability = calculateAcceptPro(efficiencyGap,NewDistrictPlan.getMeasure().getEfficiencyGap());
            double efficiencyGapThreshold = NewDistrictPlan.getMeasure().getAcceptThreshold().getEfficiencyGap();
            if(efficiencyGap < efficiencyGapThreshold){//smaller is better
                if(acceptProbability == true) {// within the probability, then accept it
                    NewDistrictPlan.getMeasure().setEfficiencyGap(efficiencyGap);
                }
                else {
                    attempts += 1;
                    NewDistrictPlan = NewDistrictPlanCopy;
                    continue;
                }
            }
            else {//violate the constraints
                attempts += 1;
                NewDistrictPlan = NewDistrictPlanCopy;
                continue;
            }

            //4> compactness
            // Polsby-Popper

            Geometry moveToGeometry = districtMoveTo.getGeometry();
            moveToGeometry.union(candidateCensusBlock.getGeometry());
            double moveToArea = moveToGeometry.getArea();
            double moveToLength = moveToGeometry.getLength();
            double moveToPP = 4 * Math.PI * (moveToArea/Math.pow(moveToLength,2));

            Geometry moveFromGeometry = maxDistrict.getGeometry();
            moveFromGeometry.difference(candidateCensusBlock.getGeometry());
            double moveFromArea = moveFromGeometry.getArea();
            double moveFromLength = moveFromGeometry.getLength();
            double moveFromPP = 4 * Math.PI * (moveFromArea/Math.pow(moveFromLength,2));
            // we choose the max of PP in the four districts as we measure
            double max = 0;
            if(moveToPP > moveFromPP){
                max = moveToPP;
            }

            boolean acceptProbability1 = calculateAcceptPro(moveToPP,NewDistrictPlan.getMeasure().getCompactness());
            boolean acceptProbability2 = calculateAcceptPro(moveFromPP,NewDistrictPlan.getMeasure().getCompactness());
            double CompactnessThreshold = NewDistrictPlan.getMeasure().getAcceptThreshold().getCompactness();
            if(moveToPP < CompactnessThreshold && moveFromPP < CompactnessThreshold){//smaller is better
                if(acceptProbability1 == true && acceptProbability2 == true) {// within the probability, then accept it
                    NewDistrictPlan.getMeasure().setCompactness(max);
                }
                else {
                    attempts += 1;
                    NewDistrictPlan = NewDistrictPlanCopy;
                    continue;
                }
            }
            else {//violate the constraints
                attempts += 1;
                NewDistrictPlan = NewDistrictPlanCopy;
                continue;
            }
            NewDistrictPlan.update();
            // set the census block

        }
    }

    public boolean calculateAcceptPro (double newChange,double oriChange){
        double acceptPro = 0;
        acceptPro = Math.exp(-((newChange-oriChange)/Temperature));
        if(1 < acceptPro){
            return true;
        }
        else {
            Random rand = new Random();
            double doubleRandom = rand.nextDouble();
            if(doubleRandom < acceptPro){
                return true;
            }
            else {
                return false;
            }
        }
    }
}
