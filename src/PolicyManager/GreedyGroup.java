package PolicyManager;

import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

import java.util.List;

public class GreedyGroup extends PolicyManager {
    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers, List<WrappedReturn> listWrapped, int memoryUsage) {
        InvokerThreads invokerThreads = invokers.get(lastOne);
        if (invokerThreads.getMemoryGettingUsed() < invokerThreads.maxMemory) {
            System.out.println("invoker " + lastOne + " gets selected");
            System.out.println("\nMemory getting used: " + invokerThreads.getMemoryGettingUsed());
        } else {
            overMemoryUsage(invokerThreads, size, invokers, listWrapped);
        }

        return lastOne;
    }
}
