package Tests.PolicyManagerTests;

import ActionProxy.ActionProxy;
import ActionProxy.Foo;
import ActionProxy.FooImpl;
import Controller.Controller;
import PolicyManager.PolicyManager;
import PolicyManager.GreedyGroup;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class GreedyGroupTest {

    @Test
    public void testMain() throws ExecutionException, InterruptedException {
        // Create a PolicyManager instance
        PolicyManager policyManager = new GreedyGroup();

        // Create a Controller instance
        Controller controller = new Controller(4, 10, policyManager, 4, 1024);

        // Create a Foo instance using ActionProxy
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);

        // Record the start time
        long start = System.currentTimeMillis();

        // Execute the loop ten times
        for (int i = 0; i < 100; i++) {
            System.out.println("iteracio: " + i);
            aFoo.doAnother(5);
        }

        // Record the end time
        long end = System.currentTimeMillis();

        // Print the execution time
        System.out.println("Time taken to run the loop: " + (end - start) + "ms");
        System.out.println("Test completed successfully");
    }
}
