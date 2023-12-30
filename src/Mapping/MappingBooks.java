package Mapping;

import ActionProxy.ActionProxy;
import Controller.Controller;
import PolicyManager.PolicyManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import PolicyManager.RoundRobin;
import PolicyManager.UniformGroup;
import WrappedReturn.WrappedReturn;


public class MappingBooks {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();
        System.out.println("UniformGroup, 4 invokers, 10Threads, 1024MB per invoker");
        PolicyManager policyManager = new UniformGroup();
        Controller controller = new Controller(4, 10, policyManager, 4, 1024);
        Map<String, Integer> wordCountMap = new ConcurrentHashMap<>();
        ReentrantLock lock = new ReentrantLock();
        ReentrantReadWriteLock mapLock = new ReentrantReadWriteLock();

        // Create a Foo instance using ActionProxy
        DecoratorReadBook decoratorReadBook = (DecoratorReadBook) ActionProxy.newInstance(new DecoratorReadBooks(),
                controller);
        for (int j = 1; j < 10; j++) {

            Object results = null;

            // Create a Controller instance


            try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\mlope\\IdeaProjects\\cloudPracticac\\src\\Mapping\\Books\\book" + j + ".txt"))) {
                String line;
                List<String> currentList = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (String word : words) {

                        ListAndWord listAndWord = new ListAndWord(word, currentList);
                        decoratorReadBook.filterPonctuation(listAndWord);


                        if (currentList.size() == 10) {
                            // Map each word in the current list to the number of times it appears in the book
                            for (String currentWord : currentList) {
                                MapAndWord mapAndWord = new MapAndWord(currentWord, wordCountMap);

                                decoratorReadBook.putMap(mapAndWord);

                            }

                            currentList.clear();
                        }
                    }


                }

                // Map the remaining words in the current list to the number of times they appear in the book
                if (!currentList.isEmpty()) {
                    for (String currentWord : currentList) {
                        MapAndWord mapAndWord = new MapAndWord(currentWord, wordCountMap);
                        results = decoratorReadBook.putMap(mapAndWord);

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
        decoratorReadBook.printMapCount(wordCountMap);
        long endTime = System.currentTimeMillis();
        System.out.println("Time needed to read 10 books:"+((endTime-startTime)/1000)+"s");
        System.exit(0);
    }
}
