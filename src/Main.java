import ActionProxy.ActionProxy;
import ActionProxy.FooImpl;
import ActionProxy.Foo;
import Controller.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Controller controller = new Controller(10, 128);
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);
        long start = System.currentTimeMillis();
        System.out.println("comença aqui tot");


        for(int i=0; i<10000000; i++){
            System.out.println("iteracio: "+i);
            int correct = aFoo.doAnother(5);
            System.out.println(correct);
        }


        long end = System.currentTimeMillis();

        System.out.println("Time taken to run the loop: " + (end - start) + "ms");



    }
}