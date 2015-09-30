package xyz.jmullin.drifter.animation

import xyz.jmullin.drifter.entity.Entity

class Event(done: => Unit) extends Trigger(done) {
  override def valid: Boolean = false

  override def go()(implicit e: Entity): Trigger = {
    execute()
    super.go()
  }

  override def update(implicit delta: Float, e: Entity): Unit = {
  }
}
