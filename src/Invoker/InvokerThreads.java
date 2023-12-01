package Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

public class InvokerThreads implements Invoker{
    List<Callable<String>> callableTasks = new ArrayList<>();
    ExecutorService executor;

    public InvokerThreads(int numThreads){
        executor = Executors.newFixedThreadPool(numThreads);
    }
    @Override
    public Object execute(Function<Map<String, Integer>, Integer> action, Map<String, Integer> values) {
        long start = System.nanoTime();
        Object returns = action.apply(values);
        long end = System.nanoTime();
        long totalTime = end -start;
        System.out.println("Aquest el temps total: "+totalTime);
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
