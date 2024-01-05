package Decorator

import Invoker.Invoker
import Invoker.InvokerThreads
import WrappedReturn.WrappedReturn

import scala.collection.mutable.HashMap
import scala.concurrent.Future
import scala.util.Try

import Invoker.Observer

class DecoratorInvoker extends InvokerThreads {

  val invokerThreads: InvokerThreads = null

  private val cache = HashMap.empty[String, Object]

  def this(invokerThreads: InvokerThreads) {
    this()
    this.invokerThreads = invokerThreads
  }

  override def executeAsync[R](action: Map[String, Any] => R, values: Map[String, Any], memoryUsage: Int, observer: Observer): Future[WrappedReturn[R]] = {
    val start = System.currentTimeMillis()
    val cacheString = observer.getInvokerName()
    val concatString = new StringBuilder()
    for ((key, value) <- values) {
      concatString.append(value)
    }
    concatString.append(cacheString)
    cacheString = concatString.toString()
    if (cache.contains(cacheString)) {
      Future.successful(WrappedReturn(Try { action(values) }))
    } else {
      val wrappedReturn = invokerThreads.executeAsync(action, values, memoryUsage, observer)
      cache.put(cacheString, wrappedReturn)
      val end = System.currentTimeMillis()
      println(end - start)
      wrappedReturn
    }
  }
}
