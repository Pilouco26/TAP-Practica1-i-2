package ActionProxy;

import java.util.Map;

/**
 * Created by milax on 20/10/15.
 */
public class FooImpl implements Foo {
    @Override
    public int doSomething(Object args){
        System.out.println("I am doing something ....");
        return 0;
    }

    @Override
    public int doAnother(Object args) {
        System.out.println("I am doing another thing");
        return 0;
    }

    @Override
    public void multiplyByThree(Object args){
        Map<String, Integer> a = (Map<String, Integer>)args;
        int number = a.get("0");
        System.out.println(number+" multplied by 3 is "+number*3);
    }

    public void multiply(Object[] numbers){

        int number = (int)numbers[0];
        int number2 = (int)numbers[1];
        System.out.println(number+" multplied by 3 is "+number*number2);
    }
}
