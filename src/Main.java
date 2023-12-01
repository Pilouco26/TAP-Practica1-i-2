import ActionProxy.ActionProxy;
import ActionProxy.FooImpl;
import ActionProxy.Foo;
import Controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);
        for(int i=0; i<1000000; i++){

            aFoo.multiplyByThree( 5);
            aFoo.doSomething( 0);
            aFoo.doSomething( 5);
            aFoo.doSomething( 0);
            aFoo.multiplyByThree( 0);
            aFoo.doSomething( 0);
            aFoo.doAnother(0);
        }


    controller.getAllTime();
        controller.getMax();

    }
}