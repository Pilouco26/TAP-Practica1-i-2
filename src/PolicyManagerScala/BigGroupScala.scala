package PolicyManagerScala

import Controller.Controller
import Invoker.InvokerThreads
import WrappedReturn.WrappedReturn
import PolicyManager.PolicyManager
import java.util
import java.util.List
import scala.collection.mutable.ListBuffer

class BigGroupScala extends PolicyManager {
  override def selectInvoker(size: Int, invokers: util.List[InvokerThreads], listWrapped: util.List[WrappedReturn], memoryUsage: Int): Int = {
    val invokerThreads: InvokerThreads = invokers.get(lastOne)

    if (checkMemory(memoryUsage, invokerThreads.maxMemory)) {
      lastOneCounter = 0
      lastOne += 1
      lastOne = lastOne % size
      treatFutures(listWrapped)
    } else {
      lastOneCounter += 1
    }
    println("Invoker Selected: "+ lastOne)
    lastOne
  }
}
