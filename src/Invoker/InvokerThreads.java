package Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;

public class InvokerThreads implements Invoker{
    List<Callable<String>> callableTasks = new ArrayList<>();
    ExecutorService executor;

    final int maxMemory = 100;

    int memoryGettingUsed = 0;
    int memoryUsedTotal = 0;

    public int getMemoryUsedTotal(){
        return  memoryUsedTotal;
    }

    public int getMemoryGettingUsed(){
        return  memoryGettingUsed;
    }

    public InvokerThreads(int numThreads){
        executor = Executors.newFixedThreadPool(numThreads);
    }
    @Override
    public Object execute(Function<Map<String, Integer>, Integer> action, Map<String, Integer> values, Observer observer) {
        long start = System.nanoTime();
        Object returns = action.apply(values);
        Random r= new Random();
        int memoryUsed = r.nextInt(500);
        memoryUsedTotal +=memoryUsed;
        observer.putMemoryPairInvoker(this, memoryUsed);
        memoryGettingUsed +=memoryUsed;
        System.out.println(memoryGettingUsed);
        long end = System.nanoTime();
        long totalTime = end - start;
        memoryGettingUsed-=memoryUsed;
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
    public Future<String> executeAsync(Function<Map<String, Integer>, Integer> action, Map<String, Integer> values, int sleep) throws InterruptedException {
        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(sleep);
            long start = System.nanoTime();
            System.out.println("Inici"+start);
            int result = action.apply(values);
            long end = System.nanoTime();
            System.out.println("Final"+end);
            long totalTime = end - start;
            System.out.println("Aquest el temps total: "+totalTime);
            return String.valueOf(result);
        };
        return submitTask(callableTask);


    }

}
