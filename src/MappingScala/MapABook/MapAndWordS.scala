package MappingScala.MapABook

class MapAndWordS {
  private var word: String = _

  private var wordCountMap: Map[String, Int] = _

  def this(word: String, wordCountMap: Map[String, Int]) {
    this()
    this.word = word
    this.wordCountMap = wordCountMap
  }

  def getMap: Map[String, Int] = {
    return wordCountMap
  }

  def getWord: String = {
    return word
  }
}
