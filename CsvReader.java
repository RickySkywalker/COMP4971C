import java.io.*;
import java.util.*;

public class CsvReader {

    public static LinkedList<LinkedList<String>> readCSV(String filePath){

        LinkedList< LinkedList<String> > toReturn = new LinkedList<>();
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";

        try{
            br = new BufferedReader(new FileReader(filePath));
            while((line = br.readLine()) != null){

                String[] thisLine = line.split(csvSplitBy);

                LinkedList<String> toAdd = new LinkedList<>();

                for (String curr : thisLine){
                    toAdd.addLast(curr);
                }

                toReturn.addFirst(toAdd);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return toReturn;
    }




    public static void main(String[] args){

        String filePath = "gemini_BTCUSD_2021_1min.csv";
        LinkedList<LinkedList<String>> lsOf2021 = readCSV(filePath);
        for (int i = 0; i < 20; i++){
            System.out.println(lsOf2021.get(i));
        }
    }
}
