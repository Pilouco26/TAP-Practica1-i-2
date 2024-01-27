package scalaProjects.PolicyManagerScala

import javaProject.Invoker.InvokerThreads
import javaProject.PolicyManager.PolicyManager
import javaProject.WrappedReturn.WrappedReturn
import java.util

import scala.collection.mutable.ListBuffer

class UniformGroupScala extends PolicyManager {
  def selectInvoker(size: Int, invokers: util.List[InvokerThreads], listWrapped: util.List[WrappedReturn], memoryUsage: Int): Int = {
    val invokerThreads: InvokerThreads  = invokers.get(lastOne)

    if (checkMemory(memoryUsage, invokerThreads.maxMemory)) {

      if (lastOneCounter == size) {
        lastOneCounter = 0
        lastOne += 1
        lastOne = lastOne % size
      }
      lastOneCounter += 1
    } else {
      overMemoryUsage(invokerThreads, size, invokers, listWrapped)
    }
    println("javaProject.Invoker Selected: "+ lastOne)
    lastOne
  }
}
