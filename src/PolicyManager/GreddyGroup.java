package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

import java.util.List;

public class GreddyGroup implements PolicyManager{

    int lastOne=0;
    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers,List<WrappedReturn> listWrapped) {
        InvokerThreads invokerThreads= invokers.get(lastOne);
        if(invokerThreads.getMemoryGettingUsed()<invokerThreads.maxMemory){
            System.out.println("invoker "+lastOne+" gets selected");
            System.out.println("\nMemory getting used: "+ invokerThreads.getMemoryGettingUsed());
        }
        else{
            overMemoryUsage( invokerThreads,  size,  invokers, listWrapped);
        }


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
