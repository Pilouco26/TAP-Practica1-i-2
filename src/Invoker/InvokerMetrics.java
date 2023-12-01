package Invoker;

import java.util.Map;

public class InvokerMetrics {

    private Map<String, String> assignedInvokers;
    private Map<String, Long> assignedMemory;
    private Map<String, Long> actionsTime;

    public InvokerMetrics(Map<String, String> assignedInvokers, Map<String, Long> assignedMemory, Map<String, Long> actionsTime) {
        this.assignedInvokers = assignedInvokers;
        this.assignedMemory = assignedMemory;
        this.actionsTime = actionsTime;
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
}
