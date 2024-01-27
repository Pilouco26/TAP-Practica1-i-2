package MappingScala

import ActionProxy.ActionProxy
import Controller.Controller
import MappingScala.MapABook.{MapABookS, MapABookSS, MapAndNumberS}
import MappingScala.ReadBookScala.{ReadBookScala, ReadBooksScala}
import PolicyManager.PolicyManager
import PolicyManagerScala.RoundRobinScala
import ReadBook.{ReadBook, ReadBooks2}
import WrappedReturn.WrappedReturn

import java.util.concurrent.{ConcurrentHashMap, CopyOnWriteArrayList, Future}
import scala.collection.JavaConverters._
import scala.concurrent.Await

object MappingBooksConcurrentScala extends App {

  val startTime = System.currentTimeMillis()
  val books = 10
  println("RoundRobin, 4 invokers, 10Threads, 1024MB per invoker")
  val policyManager: PolicyManager = new RoundRobinScala()
  val controller = new Controller(4, 10, policyManager, 4, 1024)
  val wordCountMap: Map[String, Int] = Map[String, Int]()
  val readBook: ReadBookScala = new ReadBooksScala()

  // Create a MapABook instance using ActionProxy
  val mapABook = ActionProxy.newInstance(new MapABookSS(), controller).asInstanceOf[MapABookS]

  var wordCount = 0
  var list: CopyOnWriteArrayList[WrappedReturn] = new CopyOnWriteArrayList[WrappedReturn]()

  for (j <- 1 until books) {
    val a:Int = j % 10
    val mapAndNumber = new MapAndNumberS(a, wordCountMap)
    val wrappeds: Any = mapABook.mapBook(mapAndNumber)
    print("break")
    list = wrappeds.asInstanceOf[CopyOnWriteArrayList[WrappedReturn]]

  }

  val futures: List[Future[Object]] = list.toArray.map(_.asInstanceOf[WrappedReturn]).map(_.future).toList



  //readBook.printMapCount(wordCountMap)

  val endTime = System.currentTimeMillis()
  println(s"Time needed to read $books books: ${(endTime - startTime) / 1000}s")
  println(s"Word count: $wordCount")
  System.exit(0)
}
