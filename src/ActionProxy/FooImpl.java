package ActionProxy;

import java.util.Map;

/**
 * Created by milax on 20/10/15.
 */
public class FooImpl implements Foo {
    @Override
    public int doSomething(Object args){
        System.out.println("cooking2");
        return 0;
    }

    @Override
    public int doAnother(Object args) {
        System.out.println("cooking");
        return 0;
    }
    @Override
    public int bored(Object args) throws InterruptedException {
        int result=0;

        Thread.sleep(2000);
        System.out.println("im bored");


        return result;
    }


    @Override
    public void multiplyByThree(Object args){
        Map<String, Integer> a = (Map<String, Integer>)args;
        int number = a.get("0");
    }

    public void multiply(Object[] numbers){

        int number = (int)numbers[0];
        int number2 = (int)numbers[1];
        System.out.println(number+" multplied by 3 is "+number*number2);
    }
}
