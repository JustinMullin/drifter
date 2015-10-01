package xyz.jmullin.drifter.entity

/**
 * A hook abstracts the concept of some process which is added to an entity, updated on each frame,
 * and will eventually "fall off" when finished.
 */
trait Hook {
  def running: Boolean
  def update(implicit delta: Float, e: Entity): Unit
}
