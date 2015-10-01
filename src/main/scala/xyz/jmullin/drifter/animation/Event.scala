package xyz.jmullin.drifter.animation

import xyz.jmullin.drifter.entity.Entity

/**
 * A trigger which will execute immediately upon starting.
 */
class Event(done: => Unit) extends Trigger(done) {
  override def running: Boolean = false

  override def go()(implicit e: Entity): Trigger = {
    execute()
    super.go()
  }

  override def update(implicit delta: Float, e: Entity): Unit = {}
}
