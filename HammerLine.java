import java.util.LinkedList;

public class HammerLine {

    public static double targetProfit = 1.20;
    public static double stopLoss = 0.9;
    public static final int oneHundredDayInMin = 60*24*100;
    public static final int twentyDayInMin = 60*24*20;
    public static String currDate = "";
    public static final int threeDayInMin = 3*24*60;




    public static double hammerLine(double expectedReturnRatePerTrade, int year, boolean whetherStopLoss){
        double stopLossNumber = 0.0;
        targetProfit = expectedReturnRatePerTrade;
        int totalNumberOfTrades = 0;
        int totalNumberOfWin = 0;
        int totalNumberOfLose = 0;

        String[][] totalTradeList = CsvReader.readCsvAsArrayInReverseWay("data_for_" + year + ".csv");


        double originalUSD = 200000.0;
        boolean inTrading = false;
        double inTradingTime = 0.0;
        double currentBTC = 0.0;
        double buyInPrice = 0.0;

        //Basic trading logic
        /**     We will use the threeDay20DayMA to judge the big trend for current trading (if current trend is go down, then changing
         * to shorting strategy if current trending is going up, the strategy should be more.
         *      Then we will use the oneHourPriceMemory to judge for the entry signal. It works as follows:
         *          1. When we do more strategy we wait for the down or flat line
         *          2. In the down or flat line, if a hammer line shows up, buy in
         *          3. TODO: Do some reading or watching Youtube videos to find the proper profit rate and whether there is a
         *             TODO: proper stopLoss signal for each trading!
         **/
        LinkedList<Double> oneHourPriceMemory = new LinkedList<>();
        LinkedList<Double> threeDay20DayMA = new LinkedList<>();



        

        for (int i = 1; i < totalTradeList.length; i++){
            String[] currTradeArr = totalTradeList[i];
            Trade currTrade = new Trade(currTradeArr);

            //Adding/update the data in threeDay20MA
            if(threeDay20DayMA.size() == threeDayInMin){
                threeDay20DayMA.poll();
                threeDay20DayMA.addLast(currTrade.MA20);
                judgeCurrentTrend(threeDay20DayMA);
            }else {
                threeDay20DayMA.addLast(currTrade.MA20);
            }

            //Adding/update the data in oneHourPriceMemory

            if (oneHourPriceMemory.size() == 60){
                oneHourPriceMemory.poll();
                oneHourPriceMemory.addLast(currTrade.MA20);
                oneHourTrend(oneHourPriceMemory);
            }else {
                oneHourPriceMemory.addLast(currTrade.MA20);
            }
        }

        return 0.0;
    }




    //This function will return 0 if it is a relatively flat trend
    //will return 1 if it is an up-trend
    //will return 2 if it is a down-trend
    public static int judgeCurrentTrend(LinkedList<Double> threeDay20DayMA){
        //Using the least squares method to determine the k of the current trend
        //TODO: Judge the trend by reasonable number
        return 0;
    }

    public static int oneHourTrend(LinkedList<Double> oneHourPriceMemory){
        //TODO: Judge the trend by reasonable number
        return 0;
    }



    //This requires the inputLs is data in minute
    public static double leastSquaresMethod(LinkedList<Double> inputLs){
        double dataLength = inputLs.size();
        double[] currData = new double[inputLs.size()];


        for (int i = 0; i < inputLs.size(); i++){
            double curr = inputLs.get(i);
            currData[i] = curr;
        }

        double sumX = 0.0;
        double sumY = 0.0;

        for (int i = 0; i < dataLength; i++){
            sumY += (double)i/60;
        }
        for (double currPrice : currData){
            sumX += currPrice;
        }
        double averageY = sumY/dataLength;
        double averageX = sumX/dataLength;

        double up = 0.0;
        double down = 0.0;

        for (int i = 0; i < dataLength; i++){
            up = up + ((double)i/60) * currData[i] - averageX * averageY;
            down += ( Math.pow(currData[i], 2) - Math.pow(averageX, 2) );
        }
        return (up/down);

    }


    public static void main(String[] args){
        hammerLine(1.1, 2019, true);
    }


}
