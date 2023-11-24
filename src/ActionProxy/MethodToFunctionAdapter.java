package ActionProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;


public class MethodToFunctionAdapter {

    public static Function<Map<String, Integer>, Integer> adaptMethod(Method method, Class<?> targetClass) {
        return (inputMap) -> {
            try {
                return (Integer) method.invoke(targetClass.newInstance(), inputMap);
            } catch (IllegalAccessException | InstantiationException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }
}

