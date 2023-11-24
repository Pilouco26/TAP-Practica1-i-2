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
        return action.apply(values);
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
            int result = action.apply(values);
            return String.valueOf(result);
        };

        return submitTask(callableTask);


    }

}
