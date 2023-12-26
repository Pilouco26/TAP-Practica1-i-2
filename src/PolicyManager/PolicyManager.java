package PolicyManager;

import Controller.Controller;
import Invoker.InvokerThreads;

import java.util.List;

/**
 * Created by pedro on 9/10/15.
 */
public interface PolicyManager {


    int selectInvoker(int size, List<InvokerThreads> invokers);
}