package Controller;

import Decorator.DecoratorInvoker;
import Invoker.InvokerThreads;
import Observer.Observer;
import PolicyManager.PolicyManager;
import WrappedReturn.WrappedReturn;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class Controller {
    public Map<String, ActionRegistered> actionsRegistered = new HashMap<>();

    public PolicyManager policyManager;

    public CopyOnWriteArrayList<WrappedReturn> listWrapped = new CopyOnWriteArrayList<WrappedReturn>();

    public Map<String, Observer> observers = new HashMap<String, Observer>();
    private List<InvokerThreads> invokers = new ArrayList<InvokerThreads>();

    int groupSize;

    public Controller(int invokersElements, int threadingLevel, PolicyManager policyManager, int groupSize, int maxMemoryThreads) {
        this.policyManager = policyManager;
        this.groupSize = groupSize;
        for (int i = 0; i < invokersElements; i++) {
            invokers.add(new DecoratorInvoker(new InvokerThreads(threadingLevel, maxMemoryThreads)));
        }

    }

    public void registerAction(String invokerName, Function<Map<String, Object>, Integer> action, int memoryUsage) {
        ActionRegistered actionRegistered = new ActionRegistered(action, memoryUsage);
        actionsRegistered.put(invokerName, actionRegistered);
    }

    public Object invokeAsync(String invokerName, Object values, CopyOnWriteArrayList<WrappedReturn> listWrapped) throws InterruptedException, ExecutionException, NoSuchMethodException {
        Function<Map<String, Object>, Integer> action = actionsRegistered.get(invokerName).getAction();
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

    public Object execute(Object values, CopyOnWriteArrayList<WrappedReturn> listWrapped, Function<Map<String, Object>, Integer> action, int memoryUsage, Observer observer) throws ExecutionException, InterruptedException, NoSuchMethodException {
        int lastOne;
        if (values instanceof Map) {
            lastOne = policyManager.selectInvoker(groupSize, invokers, listWrapped, memoryUsage);
            WrappedReturn result = invokers.get(lastOne).executeAsync(action, (Map<String, Object>) values, memoryUsage, observer);

            listWrapped.add(result);
            return result;
        } else {
            List<Map<String, Object>> valuesCasted = (List<Map<String, Object>>) values;
            return actionPack(valuesCasted, action, memoryUsage, observer);
        }
    }

    public CopyOnWriteArrayList<WrappedReturn> actionPack(List<Map<String, Object>> valuesCasted, Function<Map<String, Object>, Integer> action, int memoryUsage, Observer observer) throws ExecutionException, InterruptedException, NoSuchMethodException {
        int lastOne;
        CopyOnWriteArrayList<WrappedReturn> wrappedReturns = new CopyOnWriteArrayList<WrappedReturn>();
        if (valuesCasted != null) {
            for (int i = 0; i < valuesCasted.size(); i++) {
                lastOne = policyManager.selectInvoker(groupSize, invokers, listWrapped, memoryUsage);
                WrappedReturn result = invokers.get(lastOne).executeAsync(action, valuesCasted.get(i), memoryUsage, observer);
                wrappedReturns.add(result);
                listWrapped.add(result);
            }
        }

        return wrappedReturns;
    }

}
