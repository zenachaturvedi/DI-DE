package WordCount;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 Word Count,  takes in a text file or set of text files from a directory and outputs the number
 of occurrences for each word
 */
public class WordCount {

    private static final String inputDirName = "../wc_input";
    private static final String outputFileName = "../wc_output/wc_result.txt";

    // count the works and fill the map in
    public static Map<String, Integer> getCountMap(Scanner in, Map<String, Integer> wordCountMap ) {
        while (in.hasNext()) {

            String word = in.next().replaceAll("[^a-zA-Z -]", "").toLowerCase(); //remove the punctuations

            if ((word != "" || word != " " || !word.isEmpty())) {
                if (wordCountMap.containsKey(word)) {
                    int count = wordCountMap.get(word); // seen this word before; increment count
                    wordCountMap.put(word, count + 1);
                } else {
                    wordCountMap.put(word, 1); // first time word is seen
                }
            }
        }
        return wordCountMap;
    }

    //write to output to file
    public static void writeToFile(Map<String, Integer> wordCountMap) {
        try {
            File file = new File(outputFileName);
            if (!file.exists()) {          // if file doesnt exists, then create it
                file.createNewFile();
            }
            FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferWriter = new BufferedWriter(filewriter);

            for (String word : wordCountMap.keySet()) {
                int frequency = wordCountMap.get(word);
                bufferWriter.write(word + "  " + frequency + "\n");
            }

            bufferWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
            throws FileNotFoundException {
        // read the text into a map
        File file = new File(inputDirName); //directory with files
        //to maintain the sorted order on keys use tree map/sorted order
        Map<String, Integer> wordCountMap = new TreeMap<String, Integer>();

        File[] listOfFiles = file.listFiles();
        for (File filename : listOfFiles) { //go by files one by one
            Scanner in = new Scanner(filename);
            getCountMap(in, wordCountMap);
        }
        writeToFile(wordCountMap);

    }
}