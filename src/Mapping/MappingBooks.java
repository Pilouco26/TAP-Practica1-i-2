package Mapping;

import ActionProxy.ActionProxy;
import Controller.Controller;
import PolicyManager.PolicyManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import PolicyManager.UniformGroup;
import PolicyManager.RoundRobin;

public class MappingBooks {

    public static void main(String[] args) throws IOException, InterruptedException {

        Map<String, Integer> wordCountMap = new HashMap<>();
        PolicyManager policyManager = new RoundRobin();
        Object results = null;
        int operations = 0;

        // Create a Controller instance
        Controller controller = new Controller(4, 10, policyManager, 4, 1024);

        // Create a Foo instance using ActionProxy
        ReadBook readBook = (ReadBook) ActionProxy.newInstance( new ReadBooks(), controller);

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\mlope\\IdeaProjects\\cloudPracticac\\src\\Mapping\\book.txt"))) {
            String line;
            List<String> currentList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");

                for (String word : words) {

                    ListAndWord listAndWord = new ListAndWord(word, currentList);
                    readBook.filterPonctuation(listAndWord);
                    operations++;

                    if (currentList.size() == 10) {
                        // Map each word in the current list to the number of times it appears in the book
                        for (String currentWord : currentList) {
                            MapAndWord mapAndWord = new MapAndWord(currentWord, wordCountMap);
                          readBook.putMap(mapAndWord);
                          operations++;
                        }

                        currentList.clear();
                    }
                }
            }

            // Map the remaining words in the current list to the number of times they appear in the book
            if (!currentList.isEmpty()) {
                for (String currentWord : currentList) {
                    MapAndWord mapAndWord = new MapAndWord(currentWord, wordCountMap);
                    results = readBook.putMap(mapAndWord);
                    operations++;
                }
            }
        }
        List<Integer> list = (List<Integer>)results;
        int listNumber = 60000;
        while(operations>listNumber){

            System.out.println("listNumber:"+listNumber);
            System.out.println("operations:"+operations);
            Thread.sleep(1);
            listNumber++;

        }

        readBook.printMapCount(wordCountMap);

    }
}
