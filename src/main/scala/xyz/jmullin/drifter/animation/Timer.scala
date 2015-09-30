package xyz.jmullin.drifter.animation

import xyz.jmullin.drifter.entity.Entity

class Timer(var duration: Float, done: => Unit) extends Trigger(done) {
  var elapsed = 0f
  override def valid: Boolean = elapsed < duration

  override def update(implicit delta: Float, e: Entity): Unit = {
    elapsed += delta
    if(elapsed >= duration) {
      execute()
    }
  }
}
