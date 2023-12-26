package Invoker;

import WrappedReturn.WrappedReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.concurrent.locks.ReentrantLock;


public class InvokerThreads implements Invoker{
    List<Callable<String>> callableTasks = new ArrayList<>();
    private ExecutorService executor ;

    public final int maxMemory = 1000;

    int memoryGettingUsed = 0;
    int memoryUsedTotal = 0;
    public InvokerThreads(int numThreads){
        executor = Executors.newFixedThreadPool(numThreads);
    }
    public int getMemoryUsedTotal(){
        return  memoryUsedTotal;
    }

    public int getMemoryGettingUsed(){
        return  memoryGettingUsed;
    }
    public void setMemoryGettingUsed(int memoryGettingUsed){
        lock.lock();
        this.memoryGettingUsed-= memoryGettingUsed ;
        lock.unlock();
    }

    public synchronized void addMemoryUse(int memoryGettingUsed){
        this.memoryGettingUsed+= memoryGettingUsed ;
    }

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public Object execute(Function<Map<String, Integer>, Integer> action, Map<String, Integer> values, Observer observer) {
        long start = System.nanoTime();
        Random r= new Random();
        int memoryUsed = r.nextInt(500);
        memoryUsedTotal +=memoryUsed;
        observer.putMemoryPairInvoker(this, memoryUsed);
        addMemoryUse(memoryUsed);
        System.out.println("M invok: "+memoryGettingUsed);
        Object returns = action.apply(values);
        long end = System.nanoTime();
        long totalTime = end - start;
        observer.putActionTimePair(action+"", totalTime);
        observer.putMemoryPair(action+"", memoryUsed);
        return returns;
    }

    public Future<String> submitTask(Callable<String> callableTask){
        callableTasks.add(callableTask);
        return executor.submit(callableTask);
    }
    public void executeAllCallableTask() throws InterruptedException {
        executor.invokeAll(callableTasks);
    }






    public WrappedReturn executeAsync(Function<Map<String, Integer>, Integer> action, Map<String, Integer> values, int sleep, Observer observer) throws InterruptedException, ExecutionException {
        long start = System.nanoTime();
        Random r = new Random();
        int memoryUsed =10;
        memoryUsedTotal += memoryUsed;
        observer.putMemoryPairInvoker(this, memoryUsed);

        lock.lock();
        try {
            addMemoryUse(memoryUsed);;

            Callable<Object> callable = () -> {
                Object returns = action.apply(values);
                long end = System.nanoTime();
                long totalTime = end - start;

                observer.putActionTimePair(action + "", totalTime);
                observer.putMemoryPair(action + "", memoryUsed);

                return returns;
            };

            // Create a Future object and submit the callable function to the executor
            Future<Object> future = executor.submit(callable);
            WrappedReturn wrappedReturn = new WrappedReturn( this, future, memoryUsed);
            // Return the Future object immediately
            return wrappedReturn;
        } finally {
            lock.unlock();


        }
    }








}
