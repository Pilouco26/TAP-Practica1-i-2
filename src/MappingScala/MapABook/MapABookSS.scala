package MappingScala.MapABook


import MappingScala.ListAndWordS.ListAndWordS
import MappingScala.ReadBookScala.ReadBooksScala

import java.io.{BufferedReader, FileReader}
import java.util
import java.util.concurrent.CopyOnWriteArrayList
import scala.collection.JavaConversions._

class MapABookSS extends MapABookS {
  def mapBook(args: AnyRef): Any = {
    var wordCount = 0
    val mapArgs = args.asInstanceOf[util.Map[String, MapAndNumberS]]
    val mapAndNumber = mapArgs.get("0")
    val wordCountMap = mapAndNumber.getCurrentMap
    val j = mapAndNumber.getIndex
    val readBook = new ReadBooksScala
    var results = -1
    // Create a Controller instance
    try {
      val reader = new BufferedReader(new FileReader("C:\\Users\\mlope\\IdeaProjects\\cloudPracticac\\src\\Mapping\\Books\\book" + j + ".txt"))

      var line: String = null
      val currentList = new CopyOnWriteArrayList[String]
      line = reader.readLine
      while (line != null) {
        if (line != null) {
          val words = line.split(" ")
          for (word <- words) {
            val listAndWord = new ListAndWordS(word, currentList)
            readBook.filterPunctuation(listAndWord)
            if (currentList.size == 10) {
              // Map each word in the current list to the number of times it appears in the book

              for (currentWord <- currentList) {
                val mapAndWord = new MapAndWordS(currentWord, wordCountMap)
                readBook.putMap(mapAndWord)
                wordCount += 1
              }
              currentList.clear()
            }
          }
        }
        line = reader.readLine
      }
      // Map the remaining words in the current list to the number of times they appear in the book
      if (!currentList.isEmpty) {
        import scala.collection.JavaConversions._
        for (currentWord <- currentList) {
          val mapAndWord = new MapAndWordS(currentWord, wordCountMap)
          results = readBook.putMap(mapAndWord).asInstanceOf[Int]
          assert(results == 0)
          wordCount += 1
        }
      }

    }
    // Assuming readBook is an instance of your ReadBook class
    wordCount
  }

}
