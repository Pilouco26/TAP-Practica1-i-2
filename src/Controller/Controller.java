package Controller;

import Invoker.InvokerThreads;
import Invoker.Invoker;
import Invoker.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Controller {
    public Map<String, Function<Map<String, Integer>, Integer>> actionsRegistered= new HashMap<>();
    public Map<String, Invoker> invokerMap;

    private Map<String,Observer> observers = new HashMap<String, Observer>();

    InvokerThreads invoker;
    public Controller(){

        invoker = new InvokerThreads(10);

    }


    public void registerAction(String invokerName, Function<Map<String, Integer>, Integer> action) {
        actionsRegistered.put(invokerName, action);
    }

    public Object invoke(String invokerName, Map<String, Integer> values) {
        Function<Map<String, Integer>, Integer> action = actionsRegistered.get(invokerName);
        Observer observer = observers.get(invokerName);
        if(observer==null){
            observer = new Observer(invokerName);
            observers.put(invokerName, observer);
        }

    return  invoker.execute(action, values, observer);
    }

    public Object invokeAsync(String invokerName, Map<String, Integer> values, int sleep) throws InterruptedException {
        Function<Map<String, Integer>, Integer> action = actionsRegistered.get(invokerName);

        return  invoker.executeAsync(action, values, sleep);
    }

    public void getAllTime(){
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            // Code block to execute for each entry
            String key = entry.getKey();
            Observer observer = entry.getValue();
            System.out.println("AVERAGE TIME IS: "+key+": "+observer.calculateAverageActionTime());
            // Use 'key' and 'observer' variables here
        }
    }
    public void getMax(){
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            String key = entry.getKey();
            Observer observer = entry.getValue();
            System.out.println("MAX TIME IS: "+key+": "+observer.calculateMaxActionTime());
        }
    }
}
