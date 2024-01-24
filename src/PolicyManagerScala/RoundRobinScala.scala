package PolicyManagerScala

import Invoker.InvokerThreads
import PolicyManager.PolicyManager
import WrappedReturn.WrappedReturn

import java.util
import java.util.List
import scala.collection.mutable.ListBuffer

class RoundRobinScala extends PolicyManager {
  override def selectInvoker(size: Int, invokers: util.List[InvokerThreads], listWrapped: util.List[WrappedReturn], memoryUsage: Int): Int = {
    val invokerThreads: InvokerThreads = invokers.get(lastOne)
    if (invokerThreads.getMemoryGettingUsed() >= invokerThreads.maxMemory) {
      overMemoryUsage(invokerThreads, size, invokers, listWrapped)
    }

    lastOne += 1
    lastOne = lastOne % size
    println("Invoker Selected: "+ lastOne)
    lastOne
  }
}
