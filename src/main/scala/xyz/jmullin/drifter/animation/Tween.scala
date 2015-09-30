package xyz.jmullin.drifter.animation

import xyz.jmullin.drifter.entity.Entity

object Tween {
  import scala.math._

  class Easing(val interpolate: Float => Float) {
    def apply(n: Float) = interpolate(n)
  }
  object Linear extends Easing(n => n)
  object Sine extends Easing(n => (1+sin((n-0.5f)*Pi).toFloat)/2f)
}

class Tween(duration: Float, tick: Float => Unit, done: => Unit) extends Timer(duration, done) {
  override def update(implicit delta: Float, e: Entity): Unit = {
    tick(elapsed/duration)

    elapsed += delta
    if(elapsed >= duration) {
      execute()
      tick(1)
    }
  }
}
