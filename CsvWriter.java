import java.io.*;
import java.util.LinkedList;



public class CsvWriter {



    public static void writeCsv(LinkedList<LinkedList<String>> input, String filePath){
        File currFile = new File(filePath);
        String toWrite = "";

        while(!input.isEmpty()){
            LinkedList<String> currLs = input.poll();
            for (int i = 0; i < currLs.size()-1; i++){
                String curr = currLs.get(i);
                toWrite = toWrite + curr + ",";
            }

            System.out.println(currLs);

            toWrite = toWrite + currLs.get(currLs.size()-1) + "\n";
        }
        Utils.writeContents(currFile, toWrite);
    }


    public static void writeCsv(String[][] input, String filePath){
        File currFile = new File(filePath);
        String toWrite = "";

        for (String[] currArr : input){

            for(int i = 0; i < currArr.length-1; i++){
                toWrite = toWrite + currArr[i] + ",";
            }
            toWrite = toWrite + currArr[currArr.length-1] + "\n";
        }
        Utils.writeContents(currFile, toWrite);
    }
}
