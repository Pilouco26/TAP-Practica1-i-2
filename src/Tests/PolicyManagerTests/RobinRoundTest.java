package Tests.PolicyManagerTests;

import ActionProxy.ActionProxy;
import ActionProxy.FooImpl;
import ActionProxy.Foo;
import Controller.Controller;
import PolicyManager.PolicyManager;
import PolicyManager.RoundRobin;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class RobinRoundTest {

    @Test
    public void testMain() throws ExecutionException, InterruptedException {
        // Create a PolicyManager instance
        PolicyManager policyManager = new RoundRobin();

        // Create a Controller instance
        Controller controller = new Controller(4, 10, policyManager, 4);

        // Create a Foo instance using ActionProxy
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);
        // Record the start time
        long start = System.currentTimeMillis();

        // Execute the loop ten times
        for (int i = 0; i < 100000; i++) {
            System.out.println("iteracio: " + i);
            aFoo.doAnother(5);
            aFoo.doSomething(0);
        }

        // Record the end time
        long end = System.currentTimeMillis();

        // Print the execution time
        System.out.println("Time taken to run the loop: " + (end - start) + "ms");
        System.out.println("Test completed successfully");
    }
}
