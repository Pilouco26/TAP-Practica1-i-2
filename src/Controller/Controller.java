package Controller;

import Invoker.InvokerThreads;
import Invoker.Invoker;
import Invoker.Observer;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class Controller {
    public Map<String, Function<Map<String, Integer>, Integer>> actionsRegistered= new HashMap<>();
    public Map<String, Invoker> invokerMap;

    private Map<String,Observer> observers = new HashMap<String, Observer>();
    int invokersElements;
    List<InvokerThreads> invokers = new ArrayList<InvokerThreads>();
    public Controller(int invokersElements){
        this.invokersElements = invokersElements;
        for(int i=0; i<invokersElements; i++) {
            invokers.add(new InvokerThreads(4));
        }

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

    return  invokers.get(new Random().nextInt(invokersElements)).execute(action, values, observer);
    }

    public Future<Object> invokeAsync(String invokerName, Map<String, Integer> values, int sleep) throws InterruptedException, ExecutionException {
        Function<Map<String, Integer>, Integer> action = actionsRegistered.get(invokerName);
        Observer observer = observers.get(invokerName);
        if(observer==null){
            observer = new Observer(invokerName);
            observers.put(invokerName, observer);
        }
        Future<Object> future =  invokers.get(new Random().nextInt(invokersElements)).executeAsync(action, values, sleep);
        return future;
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
    public void getMin(){
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            String key = entry.getKey();
            Observer observer = entry.getValue();
            System.out.println("MIN TIME IS: "+key+": "+observer.calculateMinActionTime());
        }
    }

    public void getMemory(){
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            String key = entry.getKey();
            Observer observer = entry.getValue();
            System.out.println("MEMORY AVG IS: "+key+": "+observer.calculateAverageActionMemory());
        }
    }
    public void getMemoryForEachInvoker(){
        for(int i=0;i<invokers.size(); i++ ){
            System.out.println("invoker"+i+": "+invokers.get(i).getMemoryUsedTotal()+"kb");
        }
    }

    public void getMemoryGettingUsedForEachInvoker(){
        for(int i=0;i<invokers.size(); i++ ){
            System.out.println("invoker"+i+": "+invokers.get(i).getMemoryGettingUsed()+"kb");
        }
    }
}
