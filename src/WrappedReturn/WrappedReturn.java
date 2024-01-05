package WrappedReturn;

import Invoker.InvokerThreads;

import java.util.concurrent.Future;

public class WrappedReturn {

    private final InvokerThreads invoker;

    public InvokerThreads getInvoker() {

        return invoker;
    }

    public Future<Object> future;


    public int memoryUsed;

    public WrappedReturn(InvokerThreads invoker, Future<Object> future, int memoryUsed) {
        this.future = future;
        this.invoker = invoker;
        this.memoryUsed = memoryUsed;
    }
}
