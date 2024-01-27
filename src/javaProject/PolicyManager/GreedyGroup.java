package javaProject.PolicyManager;

import javaProject.Invoker.InvokerThreads;
import javaProject.WrappedReturn.WrappedReturn;

import java.util.List;

public class GreedyGroup extends PolicyManager {
    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers, List<WrappedReturn> listWrapped, int memoryUsage) {
        InvokerThreads invokerThreads = invokers.get(lastOne);
        int memoryGettingUsed = invokerThreads.getMemoryGettingUsed();
        if (memoryGettingUsed < invokerThreads.maxMemory) {
        } else {
            overMemoryUsage(invokerThreads, size, invokers, listWrapped);
        }

        return lastOne;
    }
}

