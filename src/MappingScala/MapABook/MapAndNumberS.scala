package MappingScala.MapABook

class MapAndNumberS {
  private var index: Int = 0

  private var currentMap: Map[String, Int] = null

  def this(index: Integer, currentMap: Map[String, Int]) {
    this()
    this.index = index
    this.currentMap = currentMap
  }

  def getCurrentMap: Map[String, Int] = {
    return currentMap
  }

  def getIndex: Integer = {
    return index
  }
}
