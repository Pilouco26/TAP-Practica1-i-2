package scalaProjects.PolicyManagerScala

import javaProject.Invoker.InvokerThreads
import javaProject.WrappedReturn.WrappedReturn

import javaProject.PolicyManager.PolicyManager
import java.util
import java.util.List
import scala.collection.mutable.ListBuffer

class GreedyGroupScala extends PolicyManager {
  override def selectInvoker(size: Int, invokers: util.List[InvokerThreads], listWrapped: util.List[WrappedReturn], memoryUsage: Int): Int = {
    val invokerThreads: InvokerThreads = invokers.get(lastOne)
    val memoryGettingUsed = invokerThreads.getMemoryGettingUsed
    println("javaProject.Invoker Selected: "+ lastOne)
    if (!(memoryGettingUsed < invokerThreads.maxMemory)) {
      overMemoryUsage(invokerThreads, size, invokers, listWrapped)
    }


    lastOne
  }
}
