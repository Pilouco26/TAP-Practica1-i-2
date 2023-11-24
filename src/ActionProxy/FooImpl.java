package ActionProxy;

import java.util.Map;

/**
 * Created by milax on 20/10/15.
 */
public class FooImpl implements Foo {
    @Override
    public void doSomething(Object args){
        System.out.println("I am doing something ....");
    }

    @Override
    public void doAnother(Object args) {
        System.out.println("I am doing another thing");
    }

    @Override
    public void multiplyByThree(Object args){
        Map<String, Integer> a = (Map<String, Integer>)args;
        int number = a.get("0");
        System.out.println(number+" multplied by 3 is "+number*3);
    }
}
