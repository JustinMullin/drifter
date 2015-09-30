package xyz.jmullin.drifter.animation

import xyz.jmullin.drifter.animation.Tween.Easing
import xyz.jmullin.drifter.enrich.RichGeometry._
import xyz.jmullin.drifter.entity._

object Trigger {
  def event(done: => Unit)(implicit e: Entity2D) = new Event(done)
  def delay(duration: Float)(done: => Unit)(implicit e: Entity2D) = new Timer(duration, done)
  def tween(duration: Float)(tick: Float => Unit)(implicit e: Entity2D) = new Tween(duration, tick, {})
  def tweenDone(duration: Float)(tick: Float => Unit)(done: => Unit)(implicit e: Entity2D) = new Tween(duration, tick, done)

  def moveTo(start: V2, end: V2, duration: Float, easing: Easing)(implicit e: Entity2D) = {
    tweenDone(duration) { n =>
      e.position.set(start+(end-start)*easing.interpolate(n))
    } {
      //e.position.set(end)
    }
  }
}

abstract class Trigger(f: => Unit) extends Hook {
  var next: Option[Trigger] = None

  def go()(implicit e: Entity) = {
    e.add(this)
    update(0, e)
    this
  }

  def execute()(implicit e: Entity) = {
    f
    next.foreach(_.go())
    this
  }

  def last: Trigger = next match {
    case Some(n) => n.last
    case None => this
  }

  def then(t: Trigger)(implicit e: Entity) = {
    last.next = Some(t)
    this
  }

  def label(s: String*): Trigger = {
    tags ++= s
    next.foreach(_.label(s:_*))
    this
  }
}