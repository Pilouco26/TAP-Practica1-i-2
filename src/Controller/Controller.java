package Controller;

import Invoker.InvokerThreads;
import Invoker.Invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Controller {
    public Map<String, Function<Map<String, Integer>, Integer>> actionsRegistered= new HashMap<>();
    public Map<String, Invoker> invokerMap;
    InvokerThreads primerInvoker;
    public Controller(){

        primerInvoker = new InvokerThreads();
    }


    public void registerAction(String invokerName, Function<Map<String, Integer>, Integer> action) {
        actionsRegistered.put(invokerName, action);
    }

    public Object invoke(String invokerName, Map<String, Integer> values) {
        Function<Map<String, Integer>, Integer> action = actionsRegistered.get(invokerName);

    return  primerInvoker.execute(action, values);
    }
}
