package PolicyManagerScala


import Invoker.InvokerThreads
import WrappedReturn.WrappedReturn

import scala.collection.mutable.ListBuffer

abstract class PolicyManagerScala {

  protected var lastOneCounter = 0
  protected var lastOne = 0

  def selectInvoker(size: Int, invokers: List[InvokerThreads], listWrapped: ListBuffer[WrappedReturn], memoryUsage: Int): Int

  def overMemoryUsage(invokerThreads: InvokerThreads, size: Int, invokers: List[InvokerThreads], listWrapped: ListBuffer[WrappedReturn]): Unit = {
    var invoker = invokerThreads
    while (invoker.getMemoryGettingUsed >= invoker.maxMemory) {
      lastOne += 1
      lastOne = lastOne % size
      invoker = invokers(lastOne)
      val returns = treatFutures(listWrapped)
      if (returns == 0) {
        //Just in case synchro
        for (i <- 0 until size) {
          invokers(i).setMemoryGettingUsedToZero()
        }
      }
    }
  }

  def treatFutures(listWrapped: ListBuffer[WrappedReturn]): Int = {
    for (i <- listWrapped.indices) {
      val wrapped = listWrapped(i)
      if (wrapped.future.isDone) {
        wrapped.getInvoker.setMemoryGettingUsed(wrapped.memoryUsed)
        listWrapped.remove(i)
      }
    }
    listWrapped.size
  }

  def checkMemory(memoryUsage: Int, maxMemory: Int): Boolean = {
    (lastOneCounter + 1) * memoryUsage > maxMemory
  }
}
