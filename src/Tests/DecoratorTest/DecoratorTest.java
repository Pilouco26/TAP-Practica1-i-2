package Tests.DecoratorTest;

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

public class DecoratorTest {

    @Test
    public void test() throws ExecutionException, InterruptedException, NoSuchMethodException {
        PolicyManager policyManager = new GreedyGroup();
        Controller controller = new Controller(4, 2, policyManager, 4, 512);
        Function<Map<String, ?>, Integer> f = map -> (Integer) map.get("x") + (Integer) map.get("y");
        controller.registerAction("addAction", f, 256);


        List<Map<String, Integer>> input = Arrays.asList(new Map[]{
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),

        });
        List<WrappedReturn> result = (List<WrappedReturn>) controller.invokeAsync("addAction", input, null );

        for(WrappedReturn wrap : result){
            System.out.println(wrap.future.get());
        }

        input = Arrays.asList(new Map[]{
                Map.of("x", 8, "y", 74),
                Map.of("x", 8, "y", 7),
                Map.of("x", 8, "y", 9),
                Map.of("x", 8, "y", 6),
                Map.of("x", 8, "y", 3),
                Map.of("x", 8, "y", 5),
                Map.of("x", 8, "y", 4),
                Map.of("x", 8, "y", 44),
                Map.of("x", 8, "y", 55),
                Map.of("x", 8, "y", 77),
                Map.of("x", 8, "y", 89),
                Map.of("x", 8, "y", 1),
                Map.of("x", 8, "y", 2),
                Map.of("x", 8, "y", 0),
                Map.of("x", 8, "y", 10),

        });

        result = (List<WrappedReturn>) controller.invokeAsync("addAction", input, null );

        for(WrappedReturn wrap : result){
            System.out.println(wrap.future.get());
        }

        System.exit(0);
    }
}

