import ActionProxy.ActionProxy;
import ActionProxy.FooImpl;
import ActionProxy.Foo;
import Controller.Controller;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Foo aFoo = (Foo) ActionProxy.newInstance(new FooImpl(), controller);
        aFoo.doSomething( 5);
        aFoo.doAnother(0);
        aFoo.multiplyByThree(6);
    }
}