package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

import java.util.List;


public class RoundRobin extends PolicyManager {

    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers, List<WrappedReturn> listWrapped, int memoryUsage) {
        InvokerThreads invokerThreads = invokers.get(lastOne);
        if (invokerThreads.getMemoryGettingUsed() >= invokerThreads.maxMemory) {
            overMemoryUsage(invokerThreads, size, invokers, listWrapped);
        }
        lastOne += 1;
        lastOne = lastOne % size;
        return lastOne;
    }

}
