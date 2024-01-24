package Mapping
import ActionProxy.ActionProxy
import Controller.Controller
import Mapping.{MapABook, MapABooks, MapAndNumber, ReadBook, ReadBooks2}
import PolicyManager.{PolicyManager, RoundRobin}
import WrappedReturn.WrappedReturn
import PolicyManagerScala.RoundRobinScala
import java.util.concurrent.{ConcurrentHashMap, CopyOnWriteArrayList}
import scala.collection.JavaConverters._

object MappingBooksConcurrentScala extends App {

  val startTime = System.currentTimeMillis()
  val books = 10
  println("RoundRobin, 4 invokers, 10Threads, 1024MB per invoker")
  val policyManager: PolicyManager = new RoundRobinScala()
  val controller = new Controller(4, 10, policyManager, 4, 1024)
  val wordCountMap = new ConcurrentHashMap[String, Integer]()
  val readBook: ReadBook = new ReadBooks2()

  // Create a MapABook instance using ActionProxy
  val mapABook = ActionProxy.newInstance(new MapABooks(), controller).asInstanceOf[MapABook]

  var wordCount = 0
  var list: CopyOnWriteArrayList[WrappedReturn] = new CopyOnWriteArrayList[WrappedReturn]()

  for (j <- 1 until books) {
    val a = j % 10
    val mapAndNumber = new MapAndNumber(a, wordCountMap)
    val result = mapABook.mapBook(mapAndNumber).asInstanceOf[java.util.List[WrappedReturn]]
    list.addAll(result)
  }

  wordCount = list.asScala.map(wrappedReturn => wrappedReturn.future.get().asInstanceOf[Int]).sum
  readBook.printMapCount(wordCountMap)

  val endTime = System.currentTimeMillis()
  println(s"Time needed to read $books books: ${(endTime - startTime) / 1000}s")
  println(s"Word count: $wordCount")
  System.exit(0)
}
