package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

import java.util.List;

/**
 * Created by pedro on 9/10/15.
 */
public abstract class PolicyManager {

    protected int lastOneCounter = 0;
    protected int lastOne = 0;

    public abstract int selectInvoker(int size, List<InvokerThreads> invokers, List<WrappedReturn> listWrapped, int memoryUsage);

    public void overMemoryUsage(InvokerThreads invokerThreads, int size, List<InvokerThreads> invokers, List<WrappedReturn> listWrapped) {
        while (invokerThreads.getMemoryGettingUsed() >= invokerThreads.maxMemory) {
            lastOne += 1;
            lastOne = lastOne % size;
            invokerThreads = invokers.get(lastOne);
            treatFutures(listWrapped);
        }
    }

    public void treatFutures(List<WrappedReturn> listWrapped) {
        for (int i = 0; i < listWrapped.size(); i++) {

            WrappedReturn wrapped = listWrapped.get(i);
            if (wrapped.future.isDone()) {
                wrapped.getInvoker().setMemoryGettingUsed(wrapped.memoryUsed);
                listWrapped.remove(i);
            }
        }
    }

    public boolean checkMemory(int memoryUsage, int maxMemory) {
        return (lastOneCounter + 1) * memoryUsage > maxMemory;
    }

}