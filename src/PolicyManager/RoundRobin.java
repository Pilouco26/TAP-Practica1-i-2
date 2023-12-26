package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;

import java.util.List;
import java.util.Random;

public class RoundRobin implements PolicyManager{
    int lastOne=-1;

    @Override
    public int selectInvoker(int size,  List<InvokerThreads> invokers) {
        lastOne+=1;
        lastOne = lastOne % size;
        System.out.println("invoker "+lastOne+" gets selected");
        InvokerThreads invokerThreads= invokers.get(lastOne);
        while(invokerThreads.getMemoryGettingUsed()>invokerThreads.maxMemory){
            lastOne+=1;
            lastOne = lastOne % size;
            invokerThreads= invokers.get(lastOne);
        }
        System.out.println("\nMemory getting used: "+ invokerThreads.getMemoryGettingUsed());
        return lastOne;
    }
}
