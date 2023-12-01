package ActionProxy;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import Controller.Controller;

public class ActionProxy implements InvocationHandler {

    private Controller controller;
    private Object target;

    public ActionProxy(Controller controller, Object target) {
        this.controller = controller;
        this.target = target;
    }

    public static Object newInstance(Object target, Controller controller){
        Class targetClass = target.getClass();
        Class interfaces[] = targetClass.getInterfaces();
        Method[] methods = targetClass.getMethods();

        for(int i=0; i<methods.length; i++){
            Function<Map<String, Integer>, Integer> adaptedMethod = MethodToFunctionAdapter.adaptMethod(methods[i], targetClass);
            controller.registerAction(methods[i].getName(), adaptedMethod);
        }
        return Proxy. newProxyInstance(targetClass.getClassLoader(),
                interfaces, new ActionProxy(controller, target));
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            String invokerName = method.getName();
            Map<String, Integer> arg = toMap(args);
            if (args.length == 2) {
                controller.invokeAsync(invokerName, arg, 3000);
            } else {
                controller.invoke(invokerName, arg);
            }
        } catch (Throwable e) {
            System.out.println("Error: " + e);
        }

        return 0;
    }

    public static Map<String, Integer> toMap(Object[] objects) {
        if(objects!=null){ Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < objects.length; i++) {
                if(objects[i] instanceof Object[]){
                    Object[] a = (Object[]) objects[i];
                    for(int j=0; j<a.length; j++){
                        map.put(Integer.toString(j), (Integer)a[j]);
                    }
                }
                else{                map.put(Integer.toString(i), (Integer) objects[i]);}

            }
            return map;}
       return null;
    }


}
