
package javaProject.Decorator;

import javaProject.ActionProxy.Foo;
import javaProject.ActionProxy.FooImpl;

/**
 * Created by milax on 20/10/15.
 */
public class DecoratorFooImpl extends FooImpl {

    Foo fooImpl;
    public DecoratorFooImpl(Foo fooImpl){
        super();
        this.fooImpl = fooImpl;
    }

    public Object doSomething(Object args) {
        long start = System.currentTimeMillis();
        Object object =  fooImpl.doSomething(args);
        long end = System.currentTimeMillis();
        long time = end-start;
        System.out.println("TIME:"+time);
        return object;
    }

    public Object doAnother(Object args) {
        long start = System.currentTimeMillis();
        Object object =  fooImpl.doAnother(args);
        long end = System.currentTimeMillis();
        long time = end-start;
        System.out.println("TIME:"+time);
        return object;
    }
}
