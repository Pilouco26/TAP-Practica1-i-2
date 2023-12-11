package Invoker;

import java.util.HashMap;
import java.util.Map;

public class Observer {

    private Map<String, String> assignedInvokers;

    private String invokerName;
    private Map<String, Integer> assignedMemory;

    private Map<Invoker, Integer> assignedMemoryInvoker;
    private Map<String, Long> actionsTime;



    public Observer(String invokerName) {
        this.assignedMemory = new HashMap<>();
        this.actionsTime = new HashMap<>();
        this.invokerName = invokerName;
        this. assignedMemoryInvoker = new HashMap<Invoker, Integer>();

    }

    public Map<String, String> getAssignedInvokers() {
        return assignedInvokers;
    }

    public void setAssignedInvokers(Map<String, String> assignedInvokers) {
        this.assignedInvokers = assignedInvokers;
    }

    public Map<String, Integer> getAssignedMemory() {
        return assignedMemory;
    }


    public void setAssignedMemory(Map<String, Integer> assignedMemory) {
        this.assignedMemory = assignedMemory;
    }

    public Map<String, Long> getActionsTime() {
        return actionsTime;
    }

    public void setActionsTime(Map<String, Long> actionsTime) {
        this.actionsTime = actionsTime;
    }

    public Double calculateAverageActionTime() {
        double totalTime = 0.0;
        for (Map.Entry<String, Long> entry : actionsTime.entrySet()) {
            totalTime += entry.getValue();
        }
        return totalTime / actionsTime.size();
    }

    public Double calculateAverageActionMemory() {
        double totalTime = 0.0;
        for (Map.Entry<String, Integer> entry : assignedMemory.entrySet()) {
            totalTime += entry.getValue();
        }
        return totalTime / assignedMemory.size();
    }

    public Double calculateAverageInvokerMemory() {
        double totalTime = 0.0;
        for (Map.Entry<Invoker, Integer> entry : assignedMemoryInvoker.entrySet()) {
            totalTime += entry.getValue();
        }
        return totalTime / assignedMemoryInvoker.size();
    }

    public Double calculateMaxActionTime() {
        double maxTime = 0.0;
        for (Map.Entry<String, Long> entry : actionsTime.entrySet()) {
            double currentTime = entry.getValue();
            if (currentTime > maxTime) {
                maxTime = currentTime;
            }
        }
        return maxTime;
    }
    public Double calculateMinActionTime() {
        double minTime = Double.MAX_VALUE; // Set an initial maximum value
        for (Map.Entry<String, Long> entry : actionsTime.entrySet()) {
            double currentTime = entry.getValue();
            if (currentTime < minTime) {
                minTime = currentTime;
            }
        }
        return minTime;
    }



    public void putInvokerPair(String key, String value) {
        assignedInvokers.put(key, value);
    }

    public void putMemoryPair(String invokerName, int value) {
        assignedMemory.put(invokerName, value);
    }

    public void putMemoryPairInvoker(Invoker invoker, int value) {
        assignedMemoryInvoker.put(invoker, value);
    }

    public void putActionTimePair(String invokerName, Long value) {
        actionsTime.put(invokerName, value);
    }

}
