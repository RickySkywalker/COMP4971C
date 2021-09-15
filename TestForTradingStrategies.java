import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class TestForTradingStrategies {

    public static void main(String[] args){

        TreeMap<Integer, LinkedList<Double>> totalMap = new TreeMap<>();
        //In this map, the key is year and the 0th element of the list is the rate per trade and second is the total return rate

        for (int year = 2015; year <= 2021; year++) {
            TreeMap<Double, Double> mapForTrading = new TreeMap<>();
            for (double profitRate = 1.0; profitRate < 1.3; profitRate += 0.001) {
                double currProfitRate = TradingStrategies.hammerLine(profitRate, year, false);
                mapForTrading.put(profitRate, currProfitRate);
                System.out.println("Current rate per trade: " + profitRate);
                System.out.println("Current total rate: " + currProfitRate);
            }
            double highestProfitRatePerTrade = 1.0;
            double highestTotalProfitRate = 1.0;
            for (Map.Entry<Double, Double> entry : mapForTrading.entrySet()) {
                double currProfitRate = entry.getKey();
                double currTotalProfitRate = entry.getValue();

                if (currTotalProfitRate > highestTotalProfitRate) {
                    highestProfitRatePerTrade = currProfitRate;
                    highestTotalProfitRate = currTotalProfitRate;
                }
            }
            System.out.println("Highest profit rate is: " + highestTotalProfitRate);
            System.out.println("It happens when profit rate per trade is: " + highestProfitRatePerTrade);
            LinkedList<Double> thisYearDetail = new LinkedList<>();
            thisYearDetail.add(highestProfitRatePerTrade);
            thisYearDetail.add(highestTotalProfitRate);
            totalMap.put(year, thisYearDetail);
        }

        for (Map.Entry<Integer, LinkedList<Double>> entry : totalMap.entrySet()){
            LinkedList<Double> thisYearData = entry.getValue();
            int year = entry.getKey();

            System.out.println("-------------------------------------------------------------------");
            System.out.println("This year is: " + year);
            System.out.println("The total profit is: " + thisYearData.get(1));
            System.out.println("The target profit rate per trade os: " + thisYearData.get(0));
        }


    }

}
