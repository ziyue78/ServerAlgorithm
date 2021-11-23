import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class District {
    private List<Precinct> precincts;
    private int id;
    private List<CensusBlock> added;

    private List<CensusBlock> removed;
    private Population population;
    private List<Election> electionResult;

    public int getId(){
        return id;
    }

    public List<Election> getElectionResult() {
        return electionResult;
    }

    public Precinct getRandomPrecinct(){
        Random rand = new Random();
        int upperbound = precincts.size();
        int randomNum = rand.nextInt(upperbound);
        Precinct randomPrecinct = precincts.get(randomNum);
        return randomPrecinct;

    }
    public void addCensusBlock(CensusBlock addedCensusBlock){
        added.add(addedCensusBlock);
        //also update population


        // population.add(addedCensusBlock.getPopulation());

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
    public void removeCensusBlock(CensusBlock removedCensusBlock){
        removed.remove(new CensusBlock(removedCensusBlock));
        int total = population.getTotal() - removedCensusBlock.getPopulation().getTotal();
        population.setTotal(total);
        int white = population.getWhite() - removedCensusBlock.getPopulation().getWhite();
        population.setWhite(white);
        int aA = population.getAfricanAmerican() - removedCensusBlock.getPopulation().getAfricanAmerican();
        population.setAfricanAmerican(aA);
        int aI = population.getAmericanIndian() - removedCensusBlock.getPopulation().getAmericanIndian();
        population.setAmericanIndian(aI);
        int asian = population.getAsian() - removedCensusBlock.getPopulation().getAsian();
        population.setAsian(asian);
        int nativeHawaiian = population.getNativeHawaiian() - removedCensusBlock.getPopulation().getNativeHawaiian();
        population.setNativeHawaiian(nativeHawaiian);
        int other = population.getOther() - removedCensusBlock.getPopulation().getOther();
        population.setOther(other);

        int democraticCensusBlock = 0;
        int republicanCensusBlock = 0;
        int otherCensusBlock = 0;
        for(int i = 0; i < removedCensusBlock.getElectionResult().size(); i++){
            if (removedCensusBlock.getElectionResult().get(i).getParty() == (PartyType.DEMOCRATIC)) {
                democraticCensusBlock = removedCensusBlock.getElectionResult().get(i).getNumOfVotes();
            }
            else if (removedCensusBlock.getElectionResult().get(i).getParty() == (PartyType.REPUBLICAN)) {
                republicanCensusBlock = removedCensusBlock.getElectionResult().get(i).getNumOfVotes();
            }
            else if (removedCensusBlock.getElectionResult().get(i).getParty() == (PartyType.OTHER)) {
                otherCensusBlock = removedCensusBlock.getElectionResult().get(i).getNumOfVotes();
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
}
