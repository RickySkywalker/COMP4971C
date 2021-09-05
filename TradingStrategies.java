import java.util.ArrayList;
import java.util.LinkedList;

public class TradingStrategies {

    public static final double targetProfit = 1.05;
    public static final double stopLoss = 0.99;

    public static boolean hammerLineJudge(LinkedList<String> currTradeInLs){
        Trade curr = new Trade(currTradeInLs);
        double currRange = Math.abs(curr.high - curr.low);
        boolean status = false;

        if (curr.open > curr.low + curr.volume * 0.75 && curr.close > curr.low + curr.volume*0.6){
            status = true;

        }

        return  status;
    }


    /** currently testing the status of hammer candle for trading in 2020*/
    public static void main(String[] args){

        int totalNumberOfTrades = 0;
        int totalWinTime = 0;
        int totalLoseTime = 0;

        LinkedList<LinkedList<String>> tradeListOfAll = CsvReader.readCSV("gemini_BTCUSD_2021_1min.csv");


        boolean inTrading = false;
        double currentUSD = 200000.0;
        double currentBTC = 0.0;
        double buyInPrice = 0.0;

        tradeListOfAll.pop();
        tradeListOfAll.pop();


        double currTradePrice;

        while( tradeListOfAll.size() > 20){

            if (currentUSD < 0){
                break;
            }

            LinkedList<String> curr = tradeListOfAll.pop();

            if (!curr.get(2).equals("BTCUSD")){
                continue;
            }

            Trade currTrade = new Trade(curr);
            currTradePrice = (currTrade.close + currTrade.open)/2;

            if (!inTrading){

                inTrading = hammerLineJudge(curr);
                if (inTrading){
                    buyInPrice = currTradePrice;
                    currentBTC += currentUSD/buyInPrice;
                    currentUSD = 0;
                    totalNumberOfTrades++;
                    System.out.println("Trade start at: " + curr.get(1));
                }
            }else{
                if (buyInPrice * targetProfit < currTradePrice){

                    inTrading = false;
                    currentUSD = currentBTC * currTradePrice * 0.999;
                    currentBTC = 0;
                    totalWinTime++;
                    System.out.println("Now we have: " + currentUSD);
                    System.out.println("We have done: " + totalNumberOfTrades);
                    System.out.println("We have win: " + totalWinTime);
                    System.out.println("We have lose: " + totalLoseTime);
                }
                //It seems that if we cut down the stop loss, we can actually make more profits
                /*else if (buyInPrice * stopLoss > currTradePrice){

                    inTrading = false;
                    currentUSD = currentBTC * currTradePrice * 0.999;
                    currentBTC = 0;
                    totalLoseTime++;
                    System.out.println("Now we have: " + currentUSD);
                    System.out.println("We have done: " + totalNumberOfTrades);
                    System.out.println("We have win: " + totalWinTime);
                    System.out.println("We have lose: " + totalLoseTime);

                }

               */
            }

        }


        if(inTrading){
            currentUSD = currentBTC*buyInPrice;
        }

        System.out.println(currentUSD);




        System.out.println("Now we have: " + currentUSD);
        System.out.println("We have done: " + totalNumberOfTrades);
        System.out.println("We have win: " + totalWinTime);
        System.out.println("We have lose: " + totalLoseTime);

    }

}
