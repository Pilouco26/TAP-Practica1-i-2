package javaProject.Mapping;

import javaProject.ActionProxy.ActionProxy;
import javaProject.Controller.Controller;
import javaProject.MapABook.MapAndWord;
import javaProject.PolicyManager.PolicyManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import javaProject.PolicyManager.RoundRobin;
import javaProject.ReadBook.ReadBook;
import javaProject.ReadBook.ReadBooks;
import javaProject.WrappedReturn.WrappedReturn;


public class MappingBooks {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();
        System.out.println("RoundRobin, 4 invokers, 10Threads, 1024MB per invoker");
        PolicyManager policyManager = new RoundRobin();
        Controller controller = new Controller(4, 10, policyManager, 4, 1024);
        Map<String, Integer> wordCountMap = new ConcurrentHashMap<>();
        // Create a Foo instance using javaProject.ActionProxy
        ReadBook readBook = (ReadBook) ActionProxy.newInstance(new ReadBooks(),
                controller);
        int wordCount = 0;
        for (int j = 1; j < 10; j++) {
            Object results = null;

            // Create a javaProject.Controller instance


            try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\mlope\\IdeaProjects\\cloudPracticac\\src\\javaProject.Mapping\\Books\\book" + j + ".txt"))) {
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
                        results = readBook.putMap(mapAndWord);
                        wordCount++;
                    }
                }
            }
            List<WrappedReturn> list = (List<WrappedReturn>) results;
            int i = 0;
            int size = 0;
            if (list != null) {
                size = list.size();
                while (!list.isEmpty()) {
                    Future<Object> future = list.get(i).future;

                    if (future.isDone()) {
                        int zero = (int) future.get();
                        assert (zero == 0);
                        list.remove(i);
                        size -= 1;

                    }
                    i++;
                    if (size > 0)
                        i = i % size;
                }
            }


        }
        CompletableFuture<Void> printMapCountFuture = CompletableFuture.runAsync(() -> {
            // Assuming readBook is an instance of your javaProject.ReadBook class
            readBook.printMapCount(wordCountMap);
        });

        try {
            // Wait for the asynchronous task to complete
            printMapCountFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }


        long endTime = System.currentTimeMillis();
        Thread.sleep(4000);
        System.out.println("Time needed to read 10 books:" + ((endTime - startTime) / 1000) + "s");
        System.out.println("Word count:"+wordCount);
        System.exit(0);

    }
}
