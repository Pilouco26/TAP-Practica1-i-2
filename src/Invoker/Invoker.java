package Invoker;

import Observer.Observer;
import WrappedReturn.WrappedReturn;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public interface Invoker {
    Object execute(Function<Map<String, Integer>, Integer> action, Map<String, Integer> value, Observer observer);

    public WrappedReturn executeAsync(Function<Map<String, Object>, Integer> action, Map<String, Object> values, int memoryUsage, Observer observer) throws InterruptedException, ExecutionException;
}
