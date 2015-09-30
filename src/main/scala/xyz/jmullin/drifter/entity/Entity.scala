package xyz.jmullin.drifter.entity

trait Entity {
  var hooks = Set[Hook]()

  def add(h: Hook): Unit = {
    hooks += h
  }

  def remove(h: Hook): Unit = {
    hooks -= h
  }

  def clearHooks(): Unit = {
    hooks = Set()
  }

  def clearHooks(t: String): Unit = {
    hooks = hooks.filterNot(_.tags.contains(t))
  }

  def update(implicit delta: Float): Unit = {
    hooks.foreach(_.update(delta, this))
    hooks = hooks.filter(_.valid)
  }
}
