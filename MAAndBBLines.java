import java.util.*;

public class MAAndBBLines {
    public static final int oneHundredDayInMin = 60*24*100;
    public static final int twentyDayInMin = 60*24*20;
    public static Queue<Double> oneHundredDayNumber = new ArrayDeque<>();
    public static LinkedList<Double> twentyDayNumber = new LinkedList<>();
    public static final int oneDayInMin = 60*24;



    public static void oneHundredMA(){

        int indicator = 0;

        String[][] currYearTrade = CsvReader.readCsvAsArray("BTC_Data/gemini_BTCUSD_2020_1min.csv");


        for (String[] currTradeLs : currYearTrade){

            if (!currTradeLs[2].equals("BTCUSD")){
                continue;
            }

            Trade curr = new Trade(currTradeLs);
            double currPrice = (curr.open + curr.close)/2;
            if (oneHundredDayNumber.size() < oneHundredDayInMin){
                oneHundredDayNumber.add(currPrice);
            }else{
                oneHundredDayNumber.poll();
                oneHundredDayNumber.add(currPrice);
                double average = calculateAverage(oneHundredDayNumber);
                if (indicator % oneDayInMin == 0){
                    System.out.println(average);
                }
                indicator ++;
            }
        }
    }




    public static void twentyMA(){

        int indicator = 0;

        LinkedList<LinkedList<String>> currYearTrade = CsvReader.readCSV("BTC_Data/gemini_BTCUSD_2020_1min.csv");


        for (LinkedList<String> currTradeLs : currYearTrade){

            if (!currTradeLs.get(2).equals("BTCUSD")){
                continue;
            }

            Trade curr = new Trade(currTradeLs);
            double currPrice = (curr.open + curr.close)/2;
            if (twentyDayNumber.size() < twentyDayInMin){
                twentyDayNumber.add(currPrice);
            }else{
                twentyDayNumber.poll();
                twentyDayNumber.add(currPrice);
                double average = calculateAverage(twentyDayNumber);
                if (indicator % oneDayInMin == 0){
                    System.out.println(average);
                }
                indicator ++;
            }
        }
    }


    public static void BBLines(){
        int indicator = 0;

        LinkedList<LinkedList<String>> currYearTrade = CsvReader.readCSV("BTC_Data/gemini_BTCUSD_2020_1min.csv");


        for (LinkedList<String> currTradeLs : currYearTrade){

            if (!currTradeLs.get(2).equals("BTCUSD")){
                continue;
            }

            Trade curr = new Trade(currTradeLs);
            double currPrice = (curr.open + curr.close)/2;
            if (twentyDayNumber.size() < twentyDayInMin){
                twentyDayNumber.add(currPrice);
            }else{
                twentyDayNumber.poll();
                twentyDayNumber.add(currPrice);
                double average = calculateAverage(twentyDayNumber);

                double standarDiviation = StandardDiviation(twentyDayNumber);
                System.out.println("Upper Level: " + (average + 2 * standarDiviation));
                System.out.println("Average: " + average);
                System.out.println("Lower Level: " + (average - 2 * standarDiviation));
                indicator ++;
            }
        }
    }


    private static double calculateAverage(Queue<Double> currQueue){
        double total = 0.0;
        for (double curr : currQueue){
            total += curr;
        }
        return total/currQueue.size();
    }


    public static double StandardDiviation(List<Double> x) {
        int m=x.size();
        double sum=0;
        for(int i=0;i<m;i++){
            sum+=x.get(i);
        }
        double dAve=sum/m;
        double dVar=0;
        for(int i=0;i<m;i++){
            dVar+=(x.get(i)-dAve)*(x.get(i)-dAve);
        }

        return Math.sqrt(dVar/m);
    }


    public static void main(String[] args){
        oneHundredMA();
    }
}
