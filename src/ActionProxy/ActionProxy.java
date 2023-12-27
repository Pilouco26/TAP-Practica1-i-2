package ActionProxy;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

import Controller.Controller;
import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

public class ActionProxy implements InvocationHandler {
    private final ReentrantLock lock = new ReentrantLock();
    private Controller controller;
    private Object target;
    public List<WrappedReturn> wrappeds = new ArrayList<>();

    public List<Object> resultsList = new ArrayList<>();

    public ActionProxy(Controller controller, Object target) {
        this.controller = controller;
        this.target = target;
    }

    public static Object newInstance(Object target, Controller controller) {
        Class targetClass = target.getClass();
        Class interfaces[] = targetClass.getInterfaces();
        Method[] methods = targetClass.getMethods();

        for (int i = 0; i < methods.length; i++) {
            Function<Map<String, Integer>, Integer> adaptedMethod = MethodToFunctionAdapter.adaptMethod(methods[i], targetClass);
            controller.registerAction(methods[i].getName(), adaptedMethod, 10);
        }
        return Proxy.newProxyInstance(targetClass.getClassLoader(),
                interfaces, new ActionProxy(controller, target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            String invokerName = method.getName();
            Map<String, Integer> arg = toMap(args);
            treatFutures(wrappeds);
            WrappedReturn wrappedReturn = (WrappedReturn) controller.invokeAsync(invokerName, arg, wrappeds);
            if (!wrappeds.contains(wrappedReturn)) {
                wrappeds.add(wrappedReturn);
            }
        } catch (Throwable e) {
            System.out.println("Error: " + e);
        }

        return resultsList;
    }

    public static Map<String, Integer> toMap(Object[] objects) {
        if (objects != null) {
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] instanceof Object[]) {
                    Object[] a = (Object[]) objects[i];
                    for (int j = 0; j < a.length; j++) {
                        map.put(Integer.toString(j), (Integer) a[j]);
                    }
                } else {
                    map.put(Integer.toString(i), (Integer) objects[i]);
                }

            }
            return map;
        }
        return null;
    }

    public void treatFutures(List<WrappedReturn> listWrapped) throws ExecutionException, InterruptedException {
        for (int i = 0; i < listWrapped.size(); i++) {

            WrappedReturn wrapped = listWrapped.get(i);
            if (wrapped.future.isDone()) {

                wrapped.getInvoker().setMemoryGettingUsed(wrapped.memoryUsed);
                resultsList.add(wrapped.future.get());
                listWrapped.remove(i);
            }
        }
    }
}
