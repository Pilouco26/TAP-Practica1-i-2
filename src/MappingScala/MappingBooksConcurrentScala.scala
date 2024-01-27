package MappingScala

import ActionProxy.ActionProxy
import Controller.Controller
import MappingScala.MapABook.{MapABookS, MapABookSS, MapAndNumberS}
import MappingScala.ReadBookScala.{ReadBookScala, ReadBooksScala}
import PolicyManager.PolicyManager
import PolicyManagerScala.RoundRobinScala
import WrappedReturn.WrappedReturn
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException
import java.util.Iterator


import java.util

import java.util.concurrent.{ConcurrentHashMap, CopyOnWriteArrayList}


object MappingBooksConcurrentScala extends App {

  val startTime = System.currentTimeMillis()
  val books = 10
  println("RoundRobin, 4 invokers, 10Threads, 1024MB per invoker")
  val policyManager: PolicyManager = new RoundRobinScala()
  val controller = new Controller(4, 10, policyManager, 4, 1024)
  val wordCountMap: util.Map[String, Integer] = new ConcurrentHashMap[String, Integer]
  val readBook: ReadBookScala = new ReadBooksScala()

  // Create a MapABook instance using ActionProxy
  val mapABook = ActionProxy.newInstance(new MapABookSS(), controller).asInstanceOf[MapABookS]

  var wordCount = 0
  var list: CopyOnWriteArrayList[WrappedReturn] = new CopyOnWriteArrayList[WrappedReturn]()

  for (j <- 1 until books) {
    val a: Int = j % 10
    val mapAndNumber = new MapAndNumberS(a, wordCountMap)
    val wrappeds: Any = mapABook.mapBook(mapAndNumber)
    list = wrappeds.asInstanceOf[CopyOnWriteArrayList[WrappedReturn]]

  }

  private var resultList: List[Int] = List()
  while (list.size() != resultList.size) {
    val iterator: Iterator[WrappedReturn] = list.iterator()

    while (iterator.hasNext) {
      val wrappedReturn = iterator.next()
      try {
        if (wrappedReturn.future.isDone) {
          val result = wrappedReturn.future.get().asInstanceOf[Int]
          wordCount += result
          resultList = resultList :+ result
        }
      } catch {
        case e: InterruptedException => e.printStackTrace()
        case e: ExecutionException => e.printStackTrace()
        case e: TimeoutException => e.printStackTrace()
      }
    }
  }


  readBook.printMapCount(wordCountMap)

  val endTime = System.currentTimeMillis()
  println(s"Time needed to read $books books: ${(endTime - startTime) / 1000}s")
  println(s"Word count: $wordCount")
  System.exit(0)
}
