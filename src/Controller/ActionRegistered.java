package Controller;

import java.util.Map;
import java.util.function.Function;

public class ActionRegistered {
    private int memoryUsage;

    private Function<Map<String, Object>, Integer> action;

    public ActionRegistered(Function<Map<String, Object>, Integer> action, int memoryUsage) {

        this.action = action;
        this.memoryUsage = memoryUsage;

    }

    public Function<Map<String, Object>, Integer> getAction() {
        return action;
    }

    public int getMemoryUsage() {
        return memoryUsage;
    }
}
