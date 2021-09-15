import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class CsvWriterTest {

    @Test
    public void test1(){
        LinkedList<LinkedList<String>> curr = new LinkedList<>();

        for (int x = 0; x < 20; x++){

            LinkedList<String> toAdd = new LinkedList<>();
            for (int y = 0; y < 10; y++){
                toAdd.addLast(Integer.toString(y));
            }

            curr.add(toAdd);
        }


        CsvWriter.writeCsv(curr,"test.csv");
    }

}