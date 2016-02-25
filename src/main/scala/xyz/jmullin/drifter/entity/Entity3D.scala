package xyz.jmullin.drifter.entity


import com.badlogic.gdx.graphics.g3d.{Environment, ModelBatch}
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.collision.{BoundingBox, Ray}
import xyz.jmullin.drifter.enrich.RichGeometry._

/**
 * 3-dimensional entity, contains scaffolding on top of Entity for tracking 3d position.
 * An Entity3D can be added/removed from a Layer3D, which will take care of calling update/render periodically.
 */
trait Entity3D extends EntityContainer3D with Entity {
  // Implicits for local context
  implicit def self = this
  implicit def layer = parent.flatMap(_.layer)

  /**
   * This entity's parent container, or None if it's not attached.
   */
  var parent: Option[EntityContainer3D] = None

  /**
   * World position of the entity.
   */
  var position = V3(0, 0, 0)

  /**
   * Optional bounding box for this entity to determine simple collision.
   */
  var boundingBox: Option[BoundingBox] = None

  /**
   * Called to determine if a ray (typically cast from the camera) intersects with this object.
   * Default implementation checks for intersection with the entity's bounding box, if present.
   *
   * @param ray Ray cast to determine the hit point.
   * @return Some containing the point at which the entity was hit, if there was a collision.
   *         None if there was no collision.
   */
  def hitPoint(ray: Ray): Option[V3] = {
    val intersection = V3(0, 0, 0)
    boundingBox.flatMap { bounds =>
      if(Intersector.intersectRayBounds(ray, bounds, intersection))
        Some(intersection)
      else None
    }
  }

  // 3D oriented mouse events
  def touchDown(v: V3, button: Int) = true
  def touchUp(v: V3, button: Int) = false
  def touchDragged(v: V3) = false
  def mouseMoved(v: V3) = false

  /**
   * Called by the parent container when this entity is added to it.  Override to perform some
   * action after the parent container has been assigned.
   *
   * @param container The new parent container.
   */
  def create(container: EntityContainer3D): Unit = {
    parent = Some(container)
  }

  /**
   * Called by the parent container on each frame to render this entity.
   *
   * @param batch Active ModelBatch to use in rendering.
   * @param environment Environment to render within.
   */
  def render(implicit batch: ModelBatch, environment: Environment): Unit = {
    renderChildren(batch, environment)
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
}