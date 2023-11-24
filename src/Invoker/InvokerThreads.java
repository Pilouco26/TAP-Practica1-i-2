package Invoker;

import java.util.Map;
import java.util.function.Function;

public class InvokerThreads implements Invoker{

    @Override
    public Object execute(Function<Map<String, Integer>, Integer> action, Map<String, Integer> values) {
        return action.apply(values);
    }
}
