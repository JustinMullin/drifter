package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import xyz.jmullin.drifter.enrich.RichGeometry
import RichGeometry._

/**
 * 2-dimensional entity, contains scaffolding on top of Entity for tracking 2d position and orientation.
 * An Entity2D can be added/removed from a Layer2D, which will take care of calling update/render periodically.
 */
trait Entity2D extends EntityContainer2D with Entity {
  // Implicits for local context
  implicit def self = this
  implicit def layer = parent.flatMap(_.layer)

  /**
   * This entity's parent container, or None if it's not attached.
   */
  var parent: Option[EntityContainer2D] = None

  /**
   * World position of the entity.
   */
  val position = V2(0, 0)
  /**
   * World size of the entity.
   */
  val size = V2(0, 0)
  /**
   * Depth of the entity, used for Z sorting (entities with lower depth will appear on top
   * of entities with higher depth)
   */
  var depth = 0

  // Convenience methods for referring to position and size components directly
  def x = position.x
  def y = position.y
  def width = size.x
  def height = size.y
  val _bounds = new Rectangle
  def bounds = _bounds.set(x, y, width, height)

  /**
   * Called by the parent container when this entity is added to it.  Override to perform some
   * action after the parent container has been assigned.
   *
   * @param container The new parent container.
   */
  def create(container: EntityContainer2D): Unit = {}

  /**
   * Called by the parent container on each frame to render this entity.
   *
   * @param batch Active SpriteBatch to use in rendering.
   */
  def render(implicit batch: SpriteBatch): Unit = {
    renderChildren(batch)
  }

  /**
   * Called by the parent container at each tick to update this entity.
   *
   * @param delta Time in seconds elapsed since the last update tick.
   */
  override def update(implicit delta: Float): Unit = {
    updateChildren(delta)

    super.update(delta)
  }

  /**
   * Remove this entity from its parent container, if any.
   */
  def remove(): Unit = {
    layer.map(_.remove(this))
    parent = None
  }

  /**
   * Replace this entity with another, substituting its parent, position and size.
   *
   * @param e Entity to replace this with.
   */
  def replaceWith(e: Entity2D): Unit = {
    layer.map { l =>
      l.add(e)
      e.position.set(position)
      e.size.set(size)
      remove()
    }
  }

  /**
   * Returns true if the given point (in world coordinates) intersects with this entity.
   * By default this is determined using the entity's bounds (position and size) and checks
   * for collisions with children as well, returning true if any children intersect.
   *
   * @param v The point to check.
   * @return True if the point is contained in this entity
   */
  def containsPoint(v: V2): Boolean = bounds.contains(v) || children.exists(_.containsPoint(v))

  /**
   * Unprojects a screen coordinate into the world space of the parent layer.
   *
   * @param v Point to unproject.
   * @return The corresponding world space coordinate.
   */
  def unproject(v: V2) = layer.map(_.viewport.unproject(v.cpy())).getOrElse(V2(0, 0))
}