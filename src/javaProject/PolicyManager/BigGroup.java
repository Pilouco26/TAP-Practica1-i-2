package javaProject.PolicyManager;

import javaProject.Invoker.InvokerThreads;
import javaProject.WrappedReturn.WrappedReturn;

import java.util.List;

public class BigGroup extends PolicyManager {

    public int selectInvoker(int size, List<InvokerThreads> invokers, List<WrappedReturn> listWrapped, int memoryUsage) {
        InvokerThreads invokerThreads = invokers.get(lastOne);
        if (checkMemory(memoryUsage, invokerThreads.maxMemory)) {
            lastOneCounter = 0;
            lastOne++;
            lastOne = lastOne % size;
            treatFutures(listWrapped);
        } else {
            lastOneCounter += 1;
        }


        return lastOne;
    }

}


