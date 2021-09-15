import java.util.*;

public class Trade {

    public double timeStamp;
    public double open;
    public double close;
    public double high;
    public double low;
    public double volume;
    public String date;
    public double MA100;
    public double MA20;
    public double price;

    public Trade(LinkedList<String> trade){
        timeStamp = Double.parseDouble(trade.get(0));
        open = Double.parseDouble(trade.get(3));
        high = Double.parseDouble(trade.get(4));
        low = Double.parseDouble(trade.get(5));
        close = Double.parseDouble(trade.get(6));
        volume = Double.parseDouble(trade.get(7));
        date = trade.get(1);
    }


    public Trade(String[] trade){
        timeStamp = Double.parseDouble(trade[0]);
        open = Double.parseDouble(trade[3]);
        high = Double.parseDouble(trade[4]);
        low = Double.parseDouble(trade[5]);
        close = Double.parseDouble(trade[6]);
        volume = Double.parseDouble(trade[7]);
        date = trade[1];
        price = (open + close)/2;

        if (trade.length > 8){
            MA20 = Double.parseDouble(trade[9]);
            MA100 = Double.parseDouble(trade[8]);
        }else{
            MA20 = MA100 = -1;
        }
    }



    @Override
    public String toString(){
        return "[timeStamp: " + timeStamp + ", date: " + date +", open: " + open + ", high: " +
                high + ", low: " + low + ", close: " + close + ", volume: " + volume + "]";
    }

}
