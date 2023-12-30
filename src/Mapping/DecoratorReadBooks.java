package Mapping;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class DecoratorReadBooks implements DecoratorReadBook{

    private ReadBook readBook;

    public DecoratorReadBooks(){

        this.readBook = new ReadBooks();
    }
    @Override
    public Object filterPonctuation(Object args) {
        long startTime = System.currentTimeMillis();
        readBook.filterPonctuation(args);
        long endTime = System.currentTimeMillis();
        /*System.out.println("Time taken to filter punctuation: " + (endTime - startTime) + " milliseconds");*/
        return 0;
    }


    @Override
    public Object putMap(Object args) {
        long startTime = System.currentTimeMillis();
        readBook.putMap(args);
        long endTime = System.currentTimeMillis();
        /*System.out.println("Time taken to put words into map: " + (endTime - startTime) + " milliseconds");*/
        return 0;
    }

    @Override
    public Object printMapCount(Object args) {
        long startTime = System.currentTimeMillis();
        readBook.printMapCount(args);
        long endTime = System.currentTimeMillis();
        /*System.out.println("Time taken to print map count: " + (endTime - startTime) + " milliseconds");*/
        return 0;
    }

}
