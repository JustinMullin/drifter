package xyz.jmullin.drifter.animation

import xyz.jmullin.drifter.entity._

/**
 * A trigger abstracts an action to take place at some future time.  To start the trigger,
 * call go() from the context of some entity.  This will add a hook to run the trigger,
 * which will update until it finishes.
 *
 * Triggers can be chained using 'triggerA then triggerB.'  This will set up the second trigger
 * to go() after the first has completed.
 *
 * @param f Block to execute when the trigger finishes.
 */
abstract class Trigger(f: => Unit) extends Hook {
  var next: Option[Trigger] = None

  /**
   * Start the trigger, adding it to the entity in context to run until finished.
   *
   * @param e Implicit entity to add this trigger to.
   * @return this
   */
  def go()(implicit e: Entity) = {
    e.add(this)
    update(0, e)
    this
  }

  /**
   * Method to be called when this trigger has completed.  The expectation is that this
   * method will be called from the update() method in any trigger implementation.
   *
   * @param e Implicit entity context this trigger is executing under.
   * @return this
   */
  def execute()(implicit e: Entity) = {
    f
    next.foreach(_.go())
    this
  }

  /**
   * Returns the last trigger in the trigger chain, or this trigger if there are none after it.
   */
  def last: Trigger = next match {
    case Some(n) => n.last
    case None => this
  }

  /**
   * Use to schedule another trigger to execute after this one has finished.
   *
   * @param t Trigger to scheduled next.
   * @param e Implicit entity to schedule the new trigger under.
   * @return this
   */
  def >>(t: Trigger)(implicit e: Entity) = {
    last.next = Some(t)
    this
  }
}

/**
 * Utility methods for easily creating the various types of triggers.
 */
object Trigger {
  def event(done: => Unit)(implicit e: Entity2D) = new Event(done)
  def delay(duration: Float)(done: => Unit)(implicit e: Entity2D) = new Timer(duration, done)
  def tween(duration: Float)(tick: Float => Unit)(implicit e: Entity2D) = new Tween(duration, tick)
  def tweenDone(duration: Float)(tick: Float => Unit)(done: => Unit)(implicit e: Entity2D) = new Tween(duration, tick, done)
}