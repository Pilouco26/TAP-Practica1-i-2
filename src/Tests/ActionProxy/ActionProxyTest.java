package Tests.ActionProxy;

import ActionProxy.ActionProxy;
import ActionProxy.FooImpl;
import ActionProxy.Foo;
import Controller.Controller;
import PolicyManager.PolicyManager;
import PolicyManager.RoundRobin;
import org.junit.Test;
import Decorator.DecoratorFooImpl;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ActionProxyTest {

    @Test
    public void testMain() {
        // Create a PolicyManager instance
        PolicyManager policyManager = new RoundRobin();

        // Create a Controller instance
        Controller controller = new Controller(4, 10, policyManager, 4, 1024);

        // Create a Foo instance using ActionProxy
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);

        // Record the start time12
        long start = System.currentTimeMillis();
        List<Object> results = null;
        List<Object> results2 = null;
        // Execute the loop ten times
        for (int i = 0; i < 1; i++) {
            System.out.println("iteracio: " + i);
            results2 = (List<Object>) aFoo.doSomething(5);
        }

        /*Return of functions*/
        for (Object result : results2) {
            System.out.println(result);
            assert ((int) result >= 0);
        }

        // Record the end time
        long end = System.currentTimeMillis();

        // Print the execution time
        System.out.println("Time taken to run the loop: " + (end - start) + "ms");
        System.out.println("Test completed successfully");
    }
}
