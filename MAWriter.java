import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MAWriter {
    public static final int oneHundredDayInMin = 60*24*100;
    public static final int twentyDayInMin = 60*24*20;
    public static LinkedList<Double> oneHundredDayNumber = new LinkedList<>();
    public static LinkedList<Double> twentyDayNumber = new LinkedList<>();
    public static final int oneDayInMin = 60*24;



    public static void MAWriterFor100Days(){
        String[][] dataOf2018 = CsvReader.readCsvAsArray("BTC_Data/gemini_BTCUSD_2018_1min.csv");

        //This set up for the basic number for the beginning of 2019
        for (int i = dataOf2018.length - oneHundredDayInMin -20; i < dataOf2018.length; i++){
            String[] currTradeLs = dataOf2018[i];
            if (! currTradeLs[2].equals("BTCUSD")){
                continue;
            }
            Trade currTrade = new Trade(currTradeLs);
            Double currPrice = (currTrade.close + currTrade.open)/2;
            if (oneHundredDayNumber.size() < oneHundredDayInMin) {
                oneHundredDayNumber.addLast(currPrice);
            }else{
                oneHundredDayNumber.poll();
                oneHundredDayNumber.addLast(currPrice);
            }
        }


        for (int i = dataOf2018.length - twentyDayInMin - 20; i < dataOf2018.length; i++){
            String[] currTradeLs = dataOf2018[i];
            if (! currTradeLs[2].equals("BTCUSD")){
                continue;
            }
            Trade currTrade = new Trade(currTradeLs);
            Double currPrice = (currTrade.close + currTrade.open)/2;
            if (twentyDayNumber.size() < twentyDayInMin){
                twentyDayNumber.addLast(currPrice);
            }else{
                twentyDayNumber.poll();
                twentyDayNumber.addLast(currPrice);
            }
        }



        String[][] dataOf2019 = CsvReader.readCsvAsArray("BTC_Data/gemini_BTCUSD_2019_1min.csv");
        LinkedList<LinkedList<String>> thingsToWrite = new LinkedList<>();


        //This section is for the table head
        LinkedList<String> tableHead = new LinkedList<>();
        for (String curr : dataOf2018[dataOf2018.length-1]){
            tableHead.addLast(curr);
        }
        tableHead.addLast("100 days MA");
        tableHead.addLast("20 days MA");
        thingsToWrite.addLast(tableHead);



        for (int i = 0; i < dataOf2019.length; i++){

            String[] currTradeLs = dataOf2019[i];
            if (!currTradeLs[2].equals("BTCUSD")){
                continue;
            }
            Trade currTrade = new Trade(currTradeLs);
            System.out.println(currTrade);
            Double currPrice = (currTrade.close + currTrade.open)/2;
            LinkedList<String> currThingToWrite = new LinkedList<>();


            //This part is for calculating 100 days MA
            oneHundredDayNumber.poll();
            oneHundredDayNumber.addLast(currPrice);
            double curr100MA = calculateAverage(oneHundredDayNumber);


            //This part is for 20 days MA
            twentyDayNumber.poll();
            twentyDayNumber.addLast(currPrice);
            double curr20MA = calculateAverage(twentyDayNumber);


            currThingToWrite.addAll(Arrays.asList(currTradeLs));
            currThingToWrite.add(Double.toString(curr100MA));
            currThingToWrite.addLast(Double.toString(curr20MA));
            thingsToWrite.addLast(currThingToWrite);
        }


        writeCsv(thingsToWrite, "data_for_2019.csv");

    }


    public static double calculateAverage(LinkedList<Double> input){
        double total = 0.0;
        for (double curr : input){
            total += curr;
        }
        return total/input.size();
    }


    public static void writeCsv(LinkedList<LinkedList<String>> thingsToWrite, String filePath){
        CsvWriter.writeCsv(thingsToWrite, filePath);
    }


    public static void main(String[] args){
        MAWriterFor100Days();
    }


}
