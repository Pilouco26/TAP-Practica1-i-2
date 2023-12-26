package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;
import WrappedReturn.WrappedReturn;

import java.util.List;

public class BigGroup implements PolicyManager {
    @Override
    public int selectInvoker(int size, List<InvokerThreads> invokers,List<WrappedReturn> listWrapped) {
        return 0;
    }
}