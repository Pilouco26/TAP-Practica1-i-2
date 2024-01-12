package Mapping;

import ActionProxy.ActionProxy;
import Controller.Controller;
import PolicyManager.PolicyManager;
import PolicyManager.RoundRobin;
import WrappedReturn.WrappedReturn;
import scala.math.Numeric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public class MappingBooksConcurrent {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();
        System.out.println("RoundRobin, 4 invokers, 10Threads, 1024MB per invoker");
        PolicyManager policyManager = new RoundRobin();
        Controller controller = new Controller(4, 10, policyManager, 4, 1024);
        Map<String, Integer> wordCountMap = new ConcurrentHashMap<>();

        // Create a Foo instance using ActionProxy
        MapABook mapABook = (MapABook) ActionProxy.newInstance(new MapABooks(), controller);
        int wordCount = 0;
        List<WrappedReturn> list = null;
        for (int j = 1; j < 10; j++) {
            MapAndNumber mapAndNumber = new MapAndNumber(j,wordCountMap);
            list = (List<WrappedReturn>) mapABook.mapBook(mapAndNumber);
        }
        for (WrappedReturn wrappedReturn : list) {
             wordCount+=  (int)wrappedReturn.future.get();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time needed to read 10 books:" + ((endTime - startTime) / 1000) + "s");
        System.out.println("Word count:" + wordCount);
        System.exit(0);

    }
}
