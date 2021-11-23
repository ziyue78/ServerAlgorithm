import java.util.List;

public class DistrictPlan {
    private List<District> districts;
    public District findMostPopulousDistrict(){
        int maxNum = 0;
        District mostPopulousDistrict = new District();
        for(int i = 0; i < districts.size(); i++){
            District temp = new District();
            temp = districts.get(i);
            int tempTotalPopulation = temp.getPopulation().getTotal();
            if(maxNum < tempTotalPopulation){
                maxNum = tempTotalPopulation;
                mostPopulousDistrict = temp;
            }
        }
        return mostPopulousDistrict;
    }
}
