package scalaProjects.MappingScala.MapABook

import java.util
import java.util.Map

class MapAndNumberS {
  private var index: Int = 0

  private var currentMap: util.Map[String, Integer] = _

  def this(index: Integer, currentMap: util.Map[String, Integer]) {
    this()
    this.index = index
    this.currentMap = currentMap
  }

  def getCurrentMap: util.Map[String, Integer] = {
    return currentMap
  }

  def getIndex: Integer = {
    return index
  }
}
