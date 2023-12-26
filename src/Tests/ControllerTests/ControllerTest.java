package Tests.ControllerTests;

import PolicyManager.PolicyManager;
import WrappedReturn.WrappedReturn;
import org.junit.Test;

import Controller.Controller;


import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import PolicyManager.RoundRobin;
public class ControllerTest {

    @Test
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        PolicyManager policyManager = new RoundRobin();
        Controller controller = new Controller(4, 2, policyManager);
        Function<Map<String, Integer>, Integer> f = x -> x.get("x") + x.get("y");
        controller.registerAction("addAction", f, 256);
        WrappedReturn result = controller.invokeAsync("addAction", Map.of("x", 6, "y", 2), null, 0);
        Future<Object> future = result.future;
        

        System.out.println(future.get());
    }
}
