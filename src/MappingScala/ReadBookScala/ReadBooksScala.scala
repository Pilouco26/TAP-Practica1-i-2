package MappingScala.ReadBookScala

import MapABook.MapAndWord
import Mapping.ListAndWord
import MappingScala.ListAndWordS.ListAndWordS
import MappingScala.MapABook.MapAndWordS

import java.util
import java.util.Map
import scala.collection.JavaConversions.mapAsJavaMap
import java.util.concurrent._
class ReadBooksScala extends ReadBookScala {


  def putMap(args: AnyRef): Int = {
    val mapAndWord = args.asInstanceOf[MapAndWordS]
    val wordCountMap: util.concurrent.ConcurrentHashMap[String, Integer] = mapAndWord.getMap.asInstanceOf[util.concurrent.ConcurrentHashMap[String, Integer]]
    val currentWord = mapAndWord.getWord
    wordCountMap.synchronized {
      if (!wordCountMap.containsKey(currentWord)) wordCountMap.put(currentWord, 1)
      else wordCountMap.put(currentWord, wordCountMap.get(currentWord).asInstanceOf[Int] + 1)
    }
    0
  }


  def printMapCount(args: AnyRef): Int = {
    val map = args.asInstanceOf[util.Map[String, Integer]]
    // Create a new TreeMap to sort the word count map by word
    val sortedWordCountMap = new util.TreeMap[String, Integer](map)
    // Print the sorted word count map
    import scala.collection.JavaConversions._
    for (entry <- sortedWordCountMap.entrySet) {
      System.out.println(entry.getKey + " " + entry.getValue)
    }
    0
  }

  def filterPunctuation(args: AnyRef): Int = {
    val listAndWord = args.asInstanceOf[ListAndWordS]
    val currentList = listAndWord.getCurrentList
    val word = listAndWord.getWord
    val lowercaseWord = word.toLowerCase
    val filteredWord = lowercaseWord.replaceAll("[,.\\-_!?;:”)—“'(‘™’\"0123456789£•|]", "")
    currentList.synchronized {
      currentList.add(filteredWord)
    }
    0
  }

}
