package Tests.DecoratorTest;

import ActionProxy.ActionProxy;
import ActionProxy.Foo;
import ActionProxy.FooImpl;
import Controller.Controller;
import Decorator.DecoratorFooImpl;
import PolicyManager.PolicyManager;
import PolicyManager.RoundRobin;
import WrappedReturn.WrappedReturn;
import org.junit.Test;

import java.util.List;

public class DecoratorTest {

    @Test
    public void testMain() {
        // Create a PolicyManager instance
        PolicyManager policyManager = new RoundRobin();

        // Create a Controller instance
        Controller controller = new Controller(4, 10, policyManager, 4, 1024);

        // Create a Foo instance using ActionProxy
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);
        Foo Decorator = new DecoratorFooImpl(aFoo);
        // Record the start time1
        List<WrappedReturn> results = null;
        // Execute the loop ten times

        results = (List<WrappedReturn>) Decorator.doAnother(5);
        for (Object result : results) {
            System.out.println(result);
            assert ((int) result >= 0);
        }



        /*Return of functions*/

    }
}
