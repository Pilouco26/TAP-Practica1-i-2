package Invoker;

import java.util.HashMap;
import java.util.Map;

public class Observer {

    private Map<String, String> assignedInvokers;

    private String invokerName;
    private Map<String, Long> assignedMemory;
    private Map<String, Long> actionsTime;



    public Observer(String invokerName) {
        this.assignedMemory = new HashMap<>();
        this.actionsTime = new HashMap<>();
        this.invokerName = invokerName;

    }

    public Map<String, String> getAssignedInvokers() {
        return assignedInvokers;
    }

    public void setAssignedInvokers(Map<String, String> assignedInvokers) {
        this.assignedInvokers = assignedInvokers;
    }

    public Map<String, Long> getAssignedMemory() {
        return assignedMemory;
    }


    public void setAssignedMemory(Map<String, Long> assignedMemory) {
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


    public void putInvokerPair(String key, String value) {
        assignedInvokers.put(key, value);
    }

    public void putMemoryPair(String invokerName, Long value) {
        assignedMemory.put(invokerName, value);
    }

    public void putActionTimePair(String invokerName, Long value) {
        actionsTime.put(invokerName, value);
    }

}
