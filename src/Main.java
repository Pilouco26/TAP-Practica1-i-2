import ActionProxy.ActionProxy;
import ActionProxy.FooImpl;
import ActionProxy.Foo;
import Controller.Controller;
import PolicyManager.PolicyManager;
import PolicyManager.RoundRobin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        PolicyManager policyManager = new RoundRobin();
        Controller controller = new Controller(2, 10, policyManager);
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);
        long start = System.currentTimeMillis();
        System.out.println("comença aqui tot");


        for(int i=0; i<30; i++){
            System.out.println("iteracio: "+i);
            aFoo.doAnother(5);
        }


        long end = System.currentTimeMillis();

        System.out.println("Time taken to run the loop: " + (end - start) + "ms");

    }
}