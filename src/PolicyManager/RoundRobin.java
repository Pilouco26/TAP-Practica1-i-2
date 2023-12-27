package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

import java.util.List;


public class RoundRobin extends PolicyManager {

    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers, List<WrappedReturn> listWrapped, int memoryUsage) {

        System.out.println("invoker " + lastOne + " gets selected");
        InvokerThreads invokerThreads = invokers.get(lastOne);
        if (invokerThreads.getMemoryGettingUsed() >= invokerThreads.maxMemory) {
            overMemoryUsage(invokerThreads, size, invokers, listWrapped);
        }
        System.out.println("\nMemory getting used: " + invokerThreads.getMemoryGettingUsed());
        lastOne += 1;
        lastOne = lastOne % size;
        return lastOne;
    }

}
