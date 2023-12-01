package Invoker;

import java.util.Map;
import java.util.function.Function;

public interface Invoker {
    Object execute(Function<Map<String, Integer>, Integer> action, Map<String, Integer> value, Observer observer);
}
