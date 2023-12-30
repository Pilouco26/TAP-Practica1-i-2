package Controller;

import Invoker.InvokerThreads;
import Invoker.Invoker;
import Invoker.Observer;
import PolicyManager.PolicyManager;
import WrappedReturn.WrappedReturn;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class Controller {
    public Map<String, ActionRegistered> actionsRegistered = new HashMap<>();
    public Map<String, Invoker> invokerMap;
    public PolicyManager policyManager;

    public List<WrappedReturn> listWrapped = new ArrayList<WrappedReturn>();

    public Map<String, Observer> observers = new HashMap<String, Observer>();
    int invokersElements;
    List<InvokerThreads> invokers = new ArrayList<InvokerThreads>();

    int groupSize;

    public Controller(int invokersElements, int threadingLevel, PolicyManager policyManager, int groupSize, int maxMemoryThreads) {
        this.policyManager = policyManager;
        this.invokersElements = invokersElements;
        this.groupSize = groupSize;
        for (int i = 0; i < invokersElements; i++) {
            invokers.add(new InvokerThreads(threadingLevel, maxMemoryThreads));
        }

    }

    public List<InvokerThreads> getInvokers() {
        return this.invokers;
    }

    public void registerAction(String invokerName, Function<Map<String, ?>, Integer> action, int memoryUsage) {
        ActionRegistered actionRegistered = new ActionRegistered(action, memoryUsage);
        actionsRegistered.put(invokerName, actionRegistered);
    }

    public Object invokeAsync(String invokerName, Object values, List<WrappedReturn> listWrapped) throws InterruptedException, ExecutionException {
        Function<Map<String, ?>, Integer> action = actionsRegistered.get(invokerName).getAction();
        Observer observer = observers.get(invokerName);
        if (observer == null) {
            observer = new Observer(invokerName);
            observers.put(invokerName, observer);
        }

        if (listWrapped == null) {
            return execute(values, this.listWrapped, action, actionsRegistered.get(invokerName).getMemoryUsage(), observer);
        } else {
            return execute(values, listWrapped, action, actionsRegistered.get(invokerName).getMemoryUsage(), observer);
        }


    }

    public Object execute(Object values, List<WrappedReturn> listWrapped, Function<Map<String, ?>, Integer> action, int memoryUsage, Observer observer) throws ExecutionException, InterruptedException {
        int lastOne;
        if (values instanceof Map) {
            lastOne = policyManager.selectInvoker(groupSize, invokers, listWrapped, memoryUsage);
            WrappedReturn result = invokers.get(lastOne).executeAsync(action, (Map<String, Object>) values, memoryUsage, observer);
            result.future.get();

            listWrapped.add(result);
            return result;
        } else {
            List<Map<String, Object>> valuesCasted = (List<Map<String, Object>>) values;
            return actionPack(valuesCasted, action, memoryUsage, observer);
        }
    }

    public List<WrappedReturn> actionPack(List<Map<String, Object>> valuesCasted,  Function<Map<String, ?>, Integer> action, int memoryUsage, Observer observer) throws ExecutionException, InterruptedException {
        int lastOne;
        List<WrappedReturn> wrappedReturns = new ArrayList<WrappedReturn>();
        if(valuesCasted!=null){
            for (int i = 0; i < valuesCasted.size(); i++) {
                lastOne = policyManager.selectInvoker(groupSize, invokers, listWrapped, memoryUsage);
                WrappedReturn result = invokers.get(lastOne).executeAsync(action, valuesCasted.get(i), memoryUsage, observer);
                wrappedReturns.add(result);
                listWrapped.add(result);
            }
        }

        return wrappedReturns;
    }

    public void getAllTime() {
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            // Code block to execute for each entry
            String key = entry.getKey();
            Observer observer = entry.getValue();
            System.out.println("AVERAGE TIME IS: " + key + ": " + observer.calculateAverageActionTime());
            // Use 'key' and 'observer' variables here
        }
    }

    public void getMax() {
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            String key = entry.getKey();
            Observer observer = entry.getValue();
            System.out.println("MAX TIME IS: " + key + ": " + observer.calculateMaxActionTime());
        }
    }

    public void getMin() {
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            String key = entry.getKey();
            Observer observer = entry.getValue();
            System.out.println("MIN TIME IS: " + key + ": " + observer.calculateMinActionTime());
        }
    }

    public void getMemory() {
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            String key = entry.getKey();
            Observer observer = entry.getValue();
            System.out.println("MEMORY AVG IS: " + key + ": " + observer.calculateAverageActionMemory());
        }
    }

    public void getMemoryForEachInvoker() {
        for (int i = 0; i < invokers.size(); i++) {
            System.out.println("invoker" + i + ": " + invokers.get(i).getMemoryUsedTotal() + "kb");
        }
    }

    public void getMemoryGettingUsedForEachInvoker() {
        for (int i = 0; i < invokers.size(); i++) {
            System.out.println("invoker" + i + ": " + invokers.get(i).getMemoryGettingUsed() + "kb");
        }
    }
}
