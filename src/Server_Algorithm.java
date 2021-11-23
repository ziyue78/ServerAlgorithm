import java.util.*;
import java.awt.geom.*;
public class Server_Algorithm {
    private int MAX_ITER = 10000;
    private int MAX_ATTEMPTS = 50;
    private double Temperature = 0.05;
    public void moveCensusBlock(int planId){
        // 1. get districting plan
        DistrictPlanRepo Districting = new DistrictPlanRepo();
        DistrictPlan NewDistrictPlan = new DistrictPlan();
        NewDistrictPlan = Districting.findPlanById(planId);


        DistrictPlan NewDistrictPlanCopy = NewDistrictPlan.clone();
        //clone();

        //about this district plan, have we already gotten this? since session.
        // because the client need to select one district plan at first

        //2. due to the districting plan, we can have a list of district called districts
        //3. based on every district, we find border precincts
        List<Set<Precinct>> borderPrecincts = new ArrayList<Set<Precinct>>();
        List<District> districts = Districting.getDistricts();
        for(int i = 0; i < districts.size(); i++){
            PrecinctRepo precincts = new PrecinctRepo();
            Set<Precinct> tempBorderPrecincts = new HashSet<Precinct>();
            tempBorderPrecincts = precincts.findBorderPrecinctByDistrict(districts.get(i).getId());
            borderPrecincts.add(tempBorderPrecincts);
        }
        //every district have border precincts



        //PrecinctRepo precincts = new PrecinctRepo();
        //Set<Precinct> borderPrecincts = new HashSet<Precinct>();
        //borderPrecincts = precincts.findBorderPrecinctByPlan(planId);?
        //find all precincts which are on the border





        //Iterator<Precinct> iter = borderPrecincts.iterator();
        //Set<Set<CensusBlock>> borderCensusBlocks = new HashSet<HashSet<CensusBlcok>>();

//        CensusBlockRepo CensusBlocks = new CensusBlockRepo();
//        while(iter.hasNext()){//find all census block are on these precincts
//            Precinct temp = iter.next();
//            Set<CensusBlock> tempCensusBlock = new HashSet<CensusBlock>();
//            tempCensusBlock = CensusBlocks.findCensusBlockByPrecinct(temp.getid());
//            borderCensusBlocks.add(tempCensusBlock);
//            //TODO: to check with DB, Are there CensusBlock existing in the Precinct class?
//            // is list of census block a attribute of Precinct?[see in the class diagram]
//
//        }

        //4. in the loop


        int attempts = 0;
        boolean negativeMove = false;
        for(int i = 0; i < MAX_ITER; i++){
            //this step is for finding the most populous district
            int maxNum = 0;
            int maxIndex = 0;

            for(int j = 0; j < districts.size(); j++){//every district
                int tempTotalPopulation = districts.get(j).getPopulation().getTotal();
                if(maxNum < tempTotalPopulation){
                    maxNum = tempTotalPopulation;
                    maxIndex = j;

                }
            }
            //the most populous district is borderPrecincts.get(maxIndex);
            Set<Precinct> listOfMostPopulous = borderPrecincts.get(maxIndex);

            //this step is for find random precinct
            Random rand = new Random();
            int upperbound = listOfMostPopulous.size();
            int randomNum = rand.nextInt(upperbound);
            Precinct candidatePrecinct = precincts.get(randomNum);


            List<CensusBlock> borderCensusBlock = candidatePrecinct.getCensusBlocks();
            List<CensusBlock> randCensusBlock = new ArrayList<CensusBlock>();
            //this step is for find CensusBlock
            // TODO:? do we need a nested list to stored census block
            for(int j = 0; j < borderCensusBlock.size(); j++){
                if(borderCensusBlock.get(j).getIsBorder() == true){
                    randCensusBlock.add(borderCensusBlock.get(j));
                }
            }
            //this step is for find random Census Block
            Random rand1 = new Random();
            CensusBlock candidateCensusBlock = randCensusBlock.get(rand.nextInt(randCensusBlock.size()));

            //to find which district census block is connected
            List<CensusBlock> neighCensusBlock = candidateCensusBlock.getNeighbors();
            int districtIdMoveTo = 0;
            District districtMoveTo = new District();
            District districtMoveFrom = new District();
            for (int j = 0; j < neighCensusBlock.size(); j++){
                districtIdMoveTo = neighCensusBlock.get(j).getDistrictId();
                if(districtIdMoveTo != candidateCensusBlock.getDistrictId()){
                    //since there are two different districts, then move candidate
                    districtMoveTo = districts.get(districtIdMoveTo);
                    districtMoveFrom = districts.get(maxIndex);
                    districts.get(districtIdMoveTo).addCensusBlock(candidateCensusBlock);//update the population
                    districts.get(maxIndex).removeCensusBlock(candidateCensusBlock);
                    break;
                }
            }


            //4.5. set the districts

            //a> set the population





            //5. calculate the measure
            //1> population equality

            int totalPopulation = 0;
            maxNum = 0;
            int minNum = 1000000;
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
            double variantPercentage = (maxNum - minNum) / (totalPopulation / districts.size());
            double equalityThreshold = NewDistrictPlan.getMeasures().getAcceptThreshold().getPopulationEqualtity();
            boolean acceptProbability = calculateAcceptPro(variantPercentage, NewDistrictPlan.getMeasures().getPopulationEquality());
            if(variantPercentage < equalityThreshold){// next
                if(acceptProbability == true) {// within the probability, then accept it
                    NewDistrictPlan.getMeasures().setPopulationEquality(variantPercentage);
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

            //finding the enacted plan
            //since there has already gotten state object
            State enactedState = new State();
            List<District> enactedDistricts = enactedState.getEnactedPlan().getDistricts();
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
            deviation = Math.sqrt(deviation);
            acceptProbability = calculateAcceptPro(deviation,NewDistrictPlan.getMeasures().getEnactedDeviation());
            double enactedDevThreshold = NewDistrictPlan.getMeasures().getAcceptThreshold().getEnactedDeviation();
            if(deviation < enactedDevThreshold){// next
                if(acceptProbability == true) {// within the probability, then accept it
                    NewDistrictPlan.getMeasures().setEnactedDeviation(deviation);
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
            int dVotesWasted = 0;
            int rVotesWasted = 0;
            int totalPlanVotes = 0;
            for (int j = 0; j < districts.size(); j++){
                int totalDistrictVotes = 0;

                int dVotes = 0;
                int rVotes = 0;
                for(int k = 0; k < districts.get(j).getElectionResult().size(); k++) {
                    if (districts.get(j).getElectionResult().get(k).getParty() == (PartyType.DEMOCRATIC)) {
                        dVotes = districts.get(j).getElectionResult().get(k).getNumOfVotes();
                        totalDistrictVotes += dVotes;
                    } else if (districts.get(j).getElectionResult().get(i).getParty() == (PartyType.REPUBLICAN)) {
                        rVotes = districts.get(j).getElectionResult().get(k).getNumOfVotes();
                        totalDistrictVotes += rVotes;
                    } else if (districts.get(j).getElectionResult().get(i).getParty() == (PartyType.OTHER)) {
                        totalDistrictVotes += districts.get(j).getElectionResult().get(k).getNumOfVotes();
                    }
                }
                totalPlanVotes += totalDistrictVotes;
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
            acceptProbability = calculateAcceptPro(newChange,NewDistrictPlan.getMeasures().getEfficiencyGap());
            double EfficiencyGapThreshold = NewDistrictPlan.getMeasures().getAcceptThreshold().getEfficiencyGap();
            if(newChange < EfficiencyGapThreshold){//smaller is better
                if(acceptProbability == true) {// within the probability, then accept it
                    NewDistrictPlan.getMeasures().setEfficiencyGap(newChange);
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
            double beforeMoveToArea = moveToGeometry.getArea();
            double beforeMoveToLength = moveToGeometry.getLength();
            double beforeMoveToPP = 4 * Math.PI * (beforeMoveToArea/Math.pow(beforeMoveToLength,2));


            moveToGeometry.union(candidateCensusBlock.getGeometry());
            double moveToArea = moveToGeometry.getArea();
            double moveToLength = moveToGeometry.getLength();
            double moveToPP = 4 * Math.PI * (moveToArea/Math.pow(moveToLength,2));


            Geometry moveFromGeometry = districtMoveFrom.getGeometry();
            moveFromGeometry.difference(candidateCensusBlock.getGeometry());
            double moveFromArea = moveFromGeometry.getArea();
            double moveFromLength = moveFromGeometry.getLength();
            double moveFromPP = 4 * Math.PI * (moveFromArea/Math.pow(moveFromLength,2));
            // average-PP = district1 PP * weight + district2 PP * weight + district4 PP * weight
            // we choose the max of PP in the four districts as we measure
            // threshold
            // PP is for district, so a list or different class have different measure type
            // 2. postprocessing python geometry???
            boolean acceptProbability1 = calculateAcceptPro(moveToPP,NewDistrictPlan.getMeasures().getCompactness());
            boolean acceptProbability2 = calculateAcceptPro(moveFromPP,NewDistrictPlan.getMeasures().getCompactness());
            double CompactnessThreshold = NewDistrictPlan.getMeasures().getAcceptThreshold().getCompactness();
            if(moveToPP < CompactnessThreshold && moveFromPP < CompactnessThreshold){//smaller is better
                if(acceptProbability1 == true && acceptProbability2 == true) {// within the probability, then accept it
                    NewDistrictPlan.getMeasures().setEfficiencyGap(newChange);
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
