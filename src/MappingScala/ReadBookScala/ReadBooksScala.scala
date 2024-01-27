package MappingScala.ReadBookScala

import MapABook.MapAndWord
import Mapping.ListAndWord
import MappingScala.ListAndWordS.ListAndWordS

import java.util

class ReadBooksScala extends ReadBookScala {


  def putMap(args: AnyRef): Int = {
    val mapAndWord = args.asInstanceOf[MapAndWord]
    val wordCountMap = mapAndWord.getMap
    val currentWord = mapAndWord.getWord
    if (!wordCountMap.containsKey(currentWord)) wordCountMap.put(currentWord, 1)
    else wordCountMap.put(currentWord, wordCountMap.get(currentWord) + 1)
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
    currentList.add(filteredWord)
    0
  }
}
