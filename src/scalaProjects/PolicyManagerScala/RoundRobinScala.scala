package scalaProjects.PolicyManagerScala

import javaProject.Invoker.InvokerThreads
import javaProject.PolicyManager.PolicyManager
import javaProject.WrappedReturn.WrappedReturn

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
    println("javaProject.Invoker Selected: "+ lastOne)
    lastOne
  }
}
