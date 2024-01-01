package Invoker;

import java.util.HashMap;
import java.util.Map;

public class Observer {

    private String invokerName;
    private Map<String, Integer> assignedMemory;

    private Map<Invoker, Integer> assignedMemoryInvoker;
    private Map<String, Long> actionsTime;


    public Observer(String invokerName) {
        this.assignedMemory = new HashMap<>();
        this.actionsTime = new HashMap<>();
        this.invokerName = invokerName;
        this.assignedMemoryInvoker = new HashMap<Invoker, Integer>();

    }


    public String getInvokerName() {
        return invokerName;
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
