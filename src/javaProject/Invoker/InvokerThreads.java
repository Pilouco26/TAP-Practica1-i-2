package javaProject.Invoker;

import javaProject.Observer.Observer;
import javaProject.WrappedReturn.WrappedReturn;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.concurrent.locks.ReentrantLock;


public class InvokerThreads implements Invoker {

    private ExecutorService executor;

    public int maxMemory;

    private final int numThreads;

    private int memoryGettingUsed = 0;
    int memoryUsedTotal = 0;

    public InvokerThreads(int numThreads, int maxMemory) {
        this.numThreads = numThreads;
        executor = Executors.newFixedThreadPool(numThreads);
        this.maxMemory = maxMemory;
    }

    public int getMemoryUsedTotal() {
        return memoryUsedTotal;
    }

    public int getMemoryGettingUsed() {
        return memoryGettingUsed;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public int getNumThreads() {
        return numThreads;
    }


    public void setMemoryGettingUsed(int memoryGettingUsed) {
        lock.lock();
        this.memoryGettingUsed -= memoryGettingUsed;
        lock.unlock();
    }

    public void setMemoryGettingUsedToZero() {
        lock.lock();
        this.memoryGettingUsed = 0;
        lock.unlock();
    }

    public synchronized void addMemoryUse(int memoryGettingUsed) {
        this.memoryGettingUsed += memoryGettingUsed;
    }

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public Object execute(Function<Map<String, Integer>, Integer> action, Map<String, Integer> values, Observer observer) {
        long start = System.nanoTime();
        Random r = new Random();
        int memoryUsed = r.nextInt(500);
        memoryUsedTotal += memoryUsed;
        observer.putMemoryPairInvoker(this, memoryUsed);
        addMemoryUse(memoryUsed);
        Object returns = action.apply(values);
        long end = System.nanoTime();
        long totalTime = end - start;
        observer.putActionTimePair(action + "", totalTime);
        observer.putMemoryPair(action + "", memoryUsed);
        return returns;
    }

    @Override
    public WrappedReturn executeAsync(Function<Map<String, Object>, Integer> action, Map<String, Object> values, int memoryUsage, Observer observer) throws InterruptedException, ExecutionException {

        memoryUsedTotal += memoryUsage;
        observer.putMemoryPairInvoker(this, memoryUsage);

        lock.lock();
        try {
            addMemoryUse(memoryUsage);

            Callable<Object> callable = () -> {
                Object returns = action.apply(values);
                observer.putMemoryPair(action + "", memoryUsage);

                return returns;
            };

            // Create a Future object and submit the callable function to the executor
            Future<Object> future = executor.submit(callable);
            // Return the Future object immediately
            return new WrappedReturn(this, future, memoryUsage);
        } finally {
            lock.unlock();
        }
    }
}
