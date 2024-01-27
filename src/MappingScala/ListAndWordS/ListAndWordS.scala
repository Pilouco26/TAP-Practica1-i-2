package MappingScala.ListAndWordS

import java.util
import java.util.List

class ListAndWordS {
  private var word: String = _

  private var currentList: util.List[String] = _

  def this(word: String, currentList: util.List[String]) {
    this()
    this.word = word
    this.currentList = currentList
  }

  def getCurrentList: util.List[String] = currentList

  def getWord: String = word

}
