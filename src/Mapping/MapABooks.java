package Mapping;

import WrappedReturn.WrappedReturn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MapABooks implements MapABook {


    public Object mapBook(Object args) throws IOException, ExecutionException, InterruptedException {
        int wordCount = 0;
        Map<String, MapAndNumber> mapArgs =  (Map<String, MapAndNumber>)args;
        MapAndNumber mapAndNumber = (MapAndNumber) mapArgs.get("0");
        Map<String, Integer> wordCountMap = mapAndNumber.getCurrentMap();
        int j = mapAndNumber.getIndex();
        ReadBook readBook = new ReadBooks2();

        int results = -1;

        // Create a Controller instance


        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\mlope\\IdeaProjects\\cloudPracticac\\src\\Mapping\\Books\\book" + j + ".txt"))) {
            String line;
            List<String> currentList = new CopyOnWriteArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    ListAndWord listAndWord = new ListAndWord(word, currentList);
                    readBook.filterPonctuation(listAndWord);


                    if (currentList.size() == 10) {
                        // Map each word in the current list to the number of times it appears in the book
                        for (String currentWord : currentList) {
                            MapAndWord mapAndWord = new MapAndWord(currentWord, wordCountMap);

                            readBook.putMap(mapAndWord);
                            wordCount++;
                        }

                        currentList.clear();
                    }
                }
            }

            // Map the remaining words in the current list to the number of times they appear in the book
            if (!currentList.isEmpty()) {
                for (String currentWord : currentList) {
                    MapAndWord mapAndWord = new MapAndWord(currentWord, wordCountMap);
                    results = (int) readBook.putMap(mapAndWord);
                    assert (results == 0);
                    wordCount++;
                }
            }
        }
        // Assuming readBook is an instance of your ReadBook class
        readBook.printMapCount(wordCountMap);


        return wordCount;
    }
}
