package scalaProjects.MappingScala.ListAndWordS

import java.util
import java.util.List
import java.util.concurrent.CopyOnWriteArrayList

class ListAndWordS {
  private var word: String = _

  private var currentList: CopyOnWriteArrayList[String] = _

  def this(word: String, currentList: CopyOnWriteArrayList[String]) {
    this()
    this.word = word
    this.currentList = currentList
  }

  def getCurrentList: util.List[String] = currentList

  def getWord: String = word

}
