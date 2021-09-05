import java.util.LinkedList;

public class TradeSet {

    public LinkedList<LinkedList<String>> core;

    public TradeSet(LinkedList<LinkedList<String>> core){
        this.core = core;
    }

    public double timeStampOf(int i){
        Trade curr = new Trade(core.get(i));
        return curr.timeStamp;
    }

    public double openOf(int i){
        Trade curr = new Trade(core.get(i));
        return curr.open;
    }

    public double closeOf(int i){
        Trade curr = new Trade(core.get(i));
        return curr.close;
    }

    public double highOf(int i){
        Trade curr = new Trade(core.get(i));
        return curr.high;
    }

    public double lowOf(int i){
        Trade curr = new Trade(core.get(i));
        return curr.low;
    }

    public double volumeOf(int i){
        Trade curr = new Trade(core.get(i));
        return curr.volume;
    }
}
