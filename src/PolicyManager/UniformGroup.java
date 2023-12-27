package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

import java.util.List;

public class UniformGroup extends PolicyManager {
    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers, List<WrappedReturn> listWrapped, int memoryUsage) {
        InvokerThreads invokerThreads = invokers.get(lastOne);
        if (invokerThreads.getMemoryGettingUsed() < invokerThreads.maxMemory) {
            if (lastOneCounter == size) {
                lastOneCounter = 0;
                lastOne++;
                lastOne = lastOne % size;
            }
            System.out.println("invoker " + lastOne + " gets selected");
            System.out.println("\nMemory getting used: " + invokerThreads.getMemoryGettingUsed());
            lastOneCounter += 1;

        } else {
            overMemoryUsage(invokerThreads, size, invokers, listWrapped);
        }


        return lastOne;
    }

}
