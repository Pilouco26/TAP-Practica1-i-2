package scalaProjects.PolicyManagerScala

import javaProject.Controller.Controller
import javaProject.Invoker.InvokerThreads
import javaProject.WrappedReturn.WrappedReturn
import javaProject.PolicyManager.PolicyManager
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
    println("javaProject.Invoker Selected: "+ lastOne)
    lastOne
  }
}
