package javaProject.Decorator;

import javaProject.Invoker.InvokerThreads;
import javaProject.WrappedReturn.WrappedReturn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import javaProject.Observer.Observer;


public class DecoratorInvoker extends InvokerThreads {

    InvokerThreads invokerThreads;

    Map<String, Object> cache = new HashMap<>();


    public DecoratorInvoker(InvokerThreads invokerThreads) {
        super(invokerThreads.getNumThreads(), invokerThreads.getMaxMemory());
        this.invokerThreads = invokerThreads;

    }

    @Override
    public WrappedReturn executeAsync(Function<Map<String, Object>, Integer> action, Map<String, Object> values, int memoryUsage, Observer observer) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        String cacheString = observer.getInvokerName();
        StringBuilder concatString = new StringBuilder();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            concatString.append(entry.getValue());
        }
        concatString.append(cacheString);
        cacheString = concatString.toString();
        if (cache.containsKey(cacheString)) {
            return (WrappedReturn)cache.get(cacheString);
        } else {
            WrappedReturn wrappedReturn = super.executeAsync(action, values, memoryUsage, observer);
            cache.put(cacheString, wrappedReturn);
            long end = System.currentTimeMillis();
            /*System.out.println(end-start);*/
            return wrappedReturn;
        }
    }

}
