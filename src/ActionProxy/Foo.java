package ActionProxy;

import java.util.Map;

/**
 * Created by milax on 20/10/15.
 */
public interface Foo {
    void doSomething(Object args);
    void doAnother(Object args);

    void multiply(Object args, Object args2);
    void multiplyByThree(Object args);
}
