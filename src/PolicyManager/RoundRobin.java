package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

import java.util.List;


public class RoundRobin implements PolicyManager{
    int lastOne=-1;

    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers,List<WrappedReturn> listWrapped) {
        lastOne+=1;
        lastOne = lastOne % size;
        System.out.println("invoker "+lastOne+" gets selected");
        InvokerThreads invokerThreads= invokers.get(lastOne);
        while(invokerThreads.getMemoryGettingUsed()>invokerThreads.maxMemory){
            lastOne+=1;
            lastOne = lastOne % size;
            invokerThreads= invokers.get(lastOne);
            if(!(listWrapped==null)){treatFutures(listWrapped);}

        }
        System.out.println("\nMemory getting used: "+ invokerThreads.getMemoryGettingUsed());
        return lastOne;
    }
    public void overMemoryUsage(InvokerThreads invokerThreads, int size, List<InvokerThreads> invokers,List<WrappedReturn> listWrapped){
        while(invokerThreads.getMemoryGettingUsed()>=invokerThreads.maxMemory){
            lastOne+=1;
            lastOne = lastOne % size;
            invokerThreads= invokers.get(lastOne);
            System.out.println("invoker "+lastOne+" gets selected");
            if(!(listWrapped==null)){treatFutures(listWrapped);}
        }
    }
    public void treatFutures(List<WrappedReturn> listWrapped){
        for(int i=0; i<listWrapped.size(); i++){

            WrappedReturn wrapped =  listWrapped.get(i);
            if(wrapped.future.isDone()){
                wrapped.getInvoker().setMemoryGettingUsed(wrapped.memoryUsed);
                listWrapped.remove(i);
            }
        }
    }
}
