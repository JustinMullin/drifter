package xyz.jmullin.drifter.entity

/**
 * Basic unit of simulation.  An entity can be updated and contain hooks which it
 * will update in turn.
 */
trait Entity {
  /**
   * Hooks this entity is currently managing.
   */
  val hooks = collection.mutable.Set[Hook]()

  /**
   * Add a hook to be managed by this entity.
   *
   * @param h Hook to add.
   */
  def add(h: Hook): Unit = {
    hooks.add(h)
  }

  /**
   * Remove a hook from management by this entity.
   *
   * @param h Hook to remove.
   */
  def remove(h: Hook): Unit = {
    hooks.remove(h)
  }

  /**
   * Remove all hooks from this entity.
   */
  def clearHooks(): Unit = {
    hooks.clear()
  }

  /**
   * Called to update the entity and process any child hooks.
   *
   * @param delta Time in seconds elapsed since the last update tick.
   */
  def update(implicit delta: Float): Unit = {
    hooks.foreach(_.update(delta, this))
    hooks.retain(_.running)
  }
}
