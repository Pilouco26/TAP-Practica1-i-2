package ActionProxy;

import java.util.Map;

/**
 * Created by milax on 20/10/15.
 */
public interface Foo {
    int doSomething(Object args);
    int doAnother(Object args);

    int bored(Object args) throws InterruptedException;
    void multiply(Object[] numbers);
    void multiplyByThree(Object args);
}
