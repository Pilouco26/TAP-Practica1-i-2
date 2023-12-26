package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;

import java.util.List;

public class UniformGroup implements PolicyManager{

    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers) {
        return 0;
    }
}
