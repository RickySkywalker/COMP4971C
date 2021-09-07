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


    public static String[][] readCsvAsArray(String filePath){
        LinkedList<LinkedList<String>> currFile = readCSV(filePath);
        int sizeOfOuterArray = currFile.size();

        String[][] toReturn = new String[sizeOfOuterArray][];
        int i = 0;

        while (!currFile.isEmpty()){
            LinkedList<String> currLs = currFile.poll();
            toReturn[i] = new String[currLs.size()];
            for (int j = 0; j < currLs.size(); j++){
                toReturn[i][j] = currLs.get(j);
            }
            i++;
        }

        return toReturn;

    }




    public static void main(String[] args){

        String filePath = "gemini_BTCUSD_2020_1min.csv";
        String[][] currFile = readCsvAsArray(filePath);

        for (int i = 0; i < currFile.length; i++){
            for (String curr : currFile[i]){
                System.out.print(curr + "\t");
            }
            System.out.println();
        }
    }
}
