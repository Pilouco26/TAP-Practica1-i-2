package Tests.PolicyManagerTests;

import PolicyManager.PolicyManager;
import WrappedReturn.WrappedReturn;
import org.junit.Test;

import Controller.Controller;


import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import PolicyManager.GreedyGroup;
import PolicyManager.UniformGroup;
import PolicyManager.BigGroup;

public class PolicyManagerTests {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        PolicyManager policyManager = new GreedyGroup();
        Controller controller = new Controller(4, 2, policyManager, 4, 512);
        Function<Map<String, ?>, Integer> f = map -> (Integer) map.get("x") + (Integer) map.get("y");
        controller.registerAction("addAction", f, 256);
        WrappedReturn result = (WrappedReturn) controller.invokeAsync("addAction", Map.of("x", 6, "y", 2), null );
        Future<Object> future = result.future;


        System.out.println(future.get());
        List<Map<String, Integer>> input = Arrays.asList(new Map[]{
                Map.of("x", 2, "y", 3),
                Map.of("x", 9, "y", 1),
                Map.of("x", 8, "y", 8),
                Map.of("x", 2, "y", 3),
                Map.of("x", 9, "y", 1),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
        });


        List<WrappedReturn> result2 = (List<WrappedReturn>) controller.invokeAsync("addAction", input, null);
        assert(result2!=null);
        for (int i = 0; i < result2.size(); i++) {
            System.out.println(result2.get(i).future.get());
        }

        System.out.println("\n\n UNIFORM GROUP: ");

        Random random = new Random();
        List<Map<String, Integer>> input2 = IntStream.range(0, 18)
                .mapToObj(i -> Map.of("x", random.nextInt(10), "y", random.nextInt(10)))
                .collect(Collectors.toList());


        PolicyManager policyManager1 = new UniformGroup();
        Controller controller1 = new Controller(6, 8, policyManager1, 3, 1024);
        Function<Map<String, ?>, Integer> f2 = map -> (Integer) map.get("x") + (Integer) map.get("y");
        controller1.registerAction("addAction", f2, 256);
        List<WrappedReturn> result3 = (List<WrappedReturn>) controller1.invokeAsync("addAction", input2, null);
        assert(result3!=null);
        for (WrappedReturn wrappedReturn : result3) {
            System.out.println(wrappedReturn.future.get());
        }

        policyManager1 = new BigGroup();
        controller1 = new Controller(6, 8, policyManager1, 6, 512);
        controller1.registerAction("addAction", f2, 256);
        System.out.println("\n\n BIG GROUP: ");
        result3 = (List<WrappedReturn>) controller1.invokeAsync("addAction", input2, null);
        for (WrappedReturn wrappedReturn : result3) {

            System.out.println(wrappedReturn.future.get());
        }
        System.exit(0);
    }
}

