package xyz.jmullin.drifter.animation

import xyz.jmullin.drifter.entity.Entity

/**
 * Trigger which counts down a specified amount of time, calling an intermediate function for each
 * step with a calculated progress alpha to allow tweening.
 *
 * @param duration Time in seconds this tween should play for.
 * @param tick Function to call on each step the tween runs with the alpha progress of the tween.
 * @param done Block to execute when finished with the tween.
 */
class Tween(duration: Float, tick: Float => Unit, done: => Unit = {}) extends Timer(duration, done) {
  override def update(implicit delta: Float, e: Entity): Unit = {
    tick(elapsed/duration)

    elapsed += delta
    if(elapsed >= duration) {
      execute()
      tick(1)
    }
  }
}