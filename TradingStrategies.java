import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;
import java.util.LinkedList;

public class TradingStrategies {

    public static double targetProfit = 1.20;
    public static double stopLoss = 0.9;
    public static final int oneHundredDayInMin = 60*24*100;
    public static final int twentyDayInMin = 60*24*20;
    public static String currDate = "";



    public static boolean hammerLineJudge(LinkedList<String> currTradeInLs){
        Trade curr = new Trade(currTradeInLs);
        double currRange = Math.abs(curr.high - curr.low);
        boolean status = curr.open > curr.low + curr.volume * 0.75 && curr.close > curr.low + curr.volume * 0.6;

        return  status;
    }


    /** currently testing the status of hammer candle for trading in 2020*/
    public static double hammerLine(){

        int totalNumberOfTrades = 0;
        int totalWinTime = 0;
        int totalLoseTime = 0;

        LinkedList<LinkedList<String>> tradeListOfAll = CsvReader.readCSV("BTC_Data/gemini_BTCUSD_2018_1min.csv");


        double originalUSD = 200000.0;
        boolean inTrading = false;
        double currentUSD = originalUSD;
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
                    System.out.println("------------------------------------------------------");
                    System.out.println("Trade start at: " + curr.get(1));
                    System.out.println("------------------------------------------------------");
                }
            }else{
                if (buyInPrice * targetProfit < currTradePrice){
                    inTrading = false;
                    currentUSD = currentBTC * currTradePrice * 0.999;
                    currentBTC = 0;
                    totalWinTime++;
                    System.out.println("------------------------------------------------------");
                    System.out.println("Now we have: " + currentUSD);
                    System.out.println("We have done: " + totalNumberOfTrades);
                    System.out.println("We have win: " + totalWinTime);
                    System.out.println("We have lose: " + totalLoseTime);
                }
                //It seems that if we cut down the stop loss, we can actually make more profits
                else if (buyInPrice * stopLoss > currTradePrice){
                    inTrading = false;
                    currentUSD = currentBTC * currTradePrice * 0.999;
                    currentBTC = 0;
                    totalLoseTime++;
                    System.out.println("------------------------------------------------------");
                    System.out.println("Now we have: " + currentUSD);
                    System.out.println("We have done: " + totalNumberOfTrades);
                    System.out.println("We have win: " + totalWinTime);
                    System.out.println("We have lose: " + totalLoseTime);
                }
                System.out.println(currTradePrice);
            }

        }
        if(inTrading){
            currentUSD = currentBTC*buyInPrice;
        }

        double profitRate = currentUSD/originalUSD;
        System.out.println(currentUSD);
        System.out.println("Now we have: " + currentUSD);
        System.out.println("The profit rate is: " + profitRate);
        System.out.println("We have done: " + totalNumberOfTrades);
        System.out.println("We have win: " + totalWinTime);
        System.out.println("We have lose: " + totalLoseTime);

        return profitRate;
    }


    public static double hammerLine(double expectedReturnRate, int year, boolean whetherStopLoss){


        double stopLossInNumber = 0.0;

        targetProfit = expectedReturnRate;
        int totalNumberOfTrades = 0;
        int totalWinTime = 0;
        int totalLoseTime = 0;

        LinkedList<LinkedList<String>> tradeListOfAll = CsvReader.readCSV("BTC_Data/gemini_BTCUSD_" + year +"_1min.csv");


        double originalUSD = 200000.0;
        boolean inTrading = false;
        double currentUSD = originalUSD;
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

            boolean nextDay = false;
            if(!currTrade.date.equals(currDate)){
                nextDay = true;
            }
            currDate = currTrade.date;

            if (!inTrading){
                inTrading = hammerLineJudge(curr);
                if (inTrading){
                    buyInPrice = currTradePrice;
                    currentBTC += currentUSD/buyInPrice;
                    currentUSD = 0;

                    stopLossInNumber = currTrade.low;

                    totalNumberOfTrades++;
                    System.out.println("------------------------------------------------------");
                    System.out.println("Trade start at: " + curr.get(1));
                    System.out.println("------------------------------------------------------");
                }
            }else{
                if (buyInPrice * targetProfit < currTradePrice){

                    inTrading = false;
                    currentUSD = currentBTC * currTradePrice * 0.999;
                    currentBTC = 0;
                    totalWinTime++;
                    System.out.println("------------------------------------------------------");
                    System.out.println("Now we have: " + currentUSD);
                    System.out.println("We have done: " + totalNumberOfTrades);
                    System.out.println("We have win: " + totalWinTime);
                    System.out.println("We have lose: " + totalLoseTime);
                    System.out.println("------------------------------------------------------");
                }
                //It seems that if we cut down the stop loss, we can actually make more profits
                //else if (buyInPrice * stopLoss > currTradePrice && whetherStopLoss){
                else if (stopLossInNumber > currTradePrice && whetherStopLoss){
                    inTrading = false;
                    currentUSD = currentBTC * currTradePrice * 0.999;
                    currentBTC = 0;
                    totalLoseTime++;

                    System.out.println("------------------------------------------------------");
                    System.out.println("Now we have: " + currentUSD);
                    System.out.println("We have done: " + totalNumberOfTrades);
                    System.out.println("We have win: " + totalWinTime);
                    System.out.println("We have lose: " + totalLoseTime);
                    System.out.println("------------------------------------------------------");
                }

                if (!nextDay) {
                    System.out.println(currTradePrice +"\n" +  currTrade.date);
                    StdDraw.pause(100);
                }
            }

        }
        if(inTrading){
            currentUSD = currentBTC*buyInPrice;
        }

        double profitRate = currentUSD/originalUSD;

        System.out.println(currentUSD);
        System.out.println("------------------------------------------------------");
        System.out.println("Now we have: " + currentUSD);
        System.out.println("The profit rate is: " + profitRate);
        System.out.println("We have done: " + totalNumberOfTrades);
        System.out.println("We have win: " + totalWinTime);
        System.out.println("We have lose: " + totalLoseTime);
        System.out.println("------------------------------------------------------");


        return profitRate;
    }


    public static void hammerLineWithTrendJudge(){

    }


    public static void main(String[] args){
        hammerLine(1.20, 2019, true);
    }



}
