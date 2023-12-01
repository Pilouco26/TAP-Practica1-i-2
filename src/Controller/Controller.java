package Controller;

import Invoker.InvokerThreads;
import Invoker.Invoker;
import Invoker.InvokerMetrics;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Controller {
    public Map<String, Function<Map<String, Integer>, Integer>> actionsRegistered= new HashMap<>();
    public Map<String, Invoker> invokerMap;

    public InvokerMetrics invokerMetrics;
    InvokerThreads invoker;
    public Controller(){

        invoker = new InvokerThreads(10);
    }


    public void registerAction(String invokerName, Function<Map<String, Integer>, Integer> action) {
        actionsRegistered.put(invokerName, action);
    }

    public Object invoke(String invokerName, Map<String, Integer> values) {
        Function<Map<String, Integer>, Integer> action = actionsRegistered.get(invokerName);

    return  invoker.execute(action, values);
    }

    public Object invokeAsync(String invokerName, Map<String, Integer> values, int sleep) throws InterruptedException {
        Function<Map<String, Integer>, Integer> action = actionsRegistered.get(invokerName);

        return  invoker.executeAsync(action, values, sleep);
    }
}
