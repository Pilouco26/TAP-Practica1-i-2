package Mapping

import ActionProxy.ActionProxy
import Controller.Controller
import PolicyManager.PolicyManager

import scala.collection.mutable.{HashMap, ListBuffer}
import scala.concurrent.{ExecutionContext, Future}
import scala.io.{BufferedReader, Source}
import scala.util.Try

import PolicyManager.RoundRobin
import PolicyManager.UniformGroup
import PolicyManager.GreedyGroup
import WrappedReturn.WrappedReturn


object MappingBooks {

  def main(args: Array[String]): Unit = {
    val startTime = System.currentTimeMillis()
    println("RoundRobin, 4 invokers, 10Threads, 1024MB per invoker")
    val policyManager = new RoundRobin()
    val controller = new Controller(4, 10, policyManager, 4, 1024)
    val wordCountMap = new HashMap[String, Int]()
    val readBook: ReadBook = ActionProxy.newInstance(new ReadBooks, controller)
    var wordCount: Int = 0

    for (j <- 1 to 2) {
      val results: List[Future[WrappedReturn[Unit]]] = ListBuffer[Future[WrappedReturn[Unit]]]()

      val bufferedReader = Source.fromFile("C:\\Users\\mlope\\IdeaProjects\\cloudPracticac\\src\\Mapping\\Books\\book" + j + ".txt").getLines.bufferedReader
      val currentList: ListBuffer[String] = ListBuffer[String]()

      for (line <- bufferedReader) {
        val words: Array[String] = line.split(" ")
        for (word <- words) {
          val listAndWord: ListAndWord = ListAndWord(word, currentList)
          val filterPonctuationFuture: Future[WrappedReturn[ListAndWord]] = readBook.filterPonctuation(listAndWord)
          results += filterPonctuationFuture

          if (currentList.size >= 10) {
            val mapAndWord: MapAndWord = MapAndWord(currentList.head, wordCountMap)
            val putMapFuture: Future[WrappedReturn[Unit]] = readBook.putMap(mapAndWord)
            results += putMapFuture
            wordCountMap.put(currentList.head, currentList.count(word => true))
            currentList.clear()
          }
        }
      }

      if (!currentList.isEmpty()) {
        val mapAndWord: MapAndWord = MapAndWord(currentList.head, wordCountMap)
        val putMapFuture: Future[WrappedReturn[Unit]] = readBook.putMap(mapAndWord)
        results += putMapFuture
        wordCountMap.put(currentList.head, currentList.count(word => true))
      }

      bufferedReader.close()

      // Collect and wait for the asynchronous tasks to complete
      Future.sequence(results).foreach(_ => ())
    }

    val printMapCountFuture: Future[Unit] = Future {
      readBook.printMapCount(wordCountMap)
    }

    printMapCountFuture.onComplete { _ =>
      val endTime = System.currentTimeMillis()
      Thread.sleep(4000)
      println("Time needed to read 10 books:" + ((endTime - startTime) / 1000) + "s")
      println("Word count:" + wordCount)
      sys.exit(0)
    }
  }
}
