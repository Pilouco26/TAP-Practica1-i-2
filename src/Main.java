import ActionProxy.ActionProxy;
import ActionProxy.FooImpl;
import ActionProxy.Foo;
import Controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller(10);
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);
        long start = System.currentTimeMillis();
        System.out.println("comença aqui tot");
        for(int i=0; i<100; i++) {
            aFoo.doSomething(5);
            aFoo.doAnother(5);
        }

        long end = System.currentTimeMillis();

        System.out.println("Time taken to run the loop: " + (end - start) + "ms");



    }
}