package xyz.jmullin.drifter.entity

abstract class Hook {
  var tags = Set[String]()
  def valid: Boolean
  def update(implicit delta: Float, e: Entity): Unit

  override def toString: String = {
    getClass.getSimpleName.replaceAll("\\$", "") + " ["+tags.mkString(", ")+"]"
  }
}
