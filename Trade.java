import java.util.*;

public class Trade {

    public double timeStamp;
    public double open;
    public double close;
    public double high;
    public double low;
    public double volume;

    public Trade(LinkedList<String> trade){
        timeStamp = Double.parseDouble(trade.get(0));
        open = Double.parseDouble(trade.get(3));
        high = Double.parseDouble(trade.get(4));
        low = Double.parseDouble(trade.get(5));
        close = Double.parseDouble(trade.get(6));
        volume = Double.parseDouble(trade.get(7));
    }



    @Override
    public String toString(){
        return "[timeStamp: " + timeStamp + ", open: " + open + ", high: " +
                high + ", low: " + low + ", close: " + close + ", volume: " + volume + "]";
    }

}
