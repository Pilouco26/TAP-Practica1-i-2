package MappingScala.MapABook

import java.util
import java.util.Map

class MapAndWordS {
  private var word: String = _

  private var wordCountMap: util.Map[String, Integer] = _

  def this(word: String, wordCountMap: util.Map[String, Integer]) {
    this()
    this.word = word
    this.wordCountMap = wordCountMap
  }

  def getMap: util.Map[String, Integer] = {
    return wordCountMap
  }

  def getWord: String = {
    return word
  }
}
