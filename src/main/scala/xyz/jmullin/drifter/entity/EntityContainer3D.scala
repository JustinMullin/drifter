package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g3d.{Environment, ModelBatch}
import xyz.jmullin.drifter.DrifterInput

/**
 * General purpose container for Entity3Ds, allows attachment and management of children entities and passes
 * through render and update events to attached children appropriately.
 */
trait EntityContainer3D extends DrifterInput {
  // Implicit layer for local context
  implicit def layer: Option[Layer3D]

  /**
   * List of all attached children.
   */
  var children = List[Entity3D]()

  /**
   * Remove an entity from this container and any of its children.
   *
   * @param e Entity to remove.
   */
  def remove(e: Entity3D): Unit = {
    children = children.filterNot(_.equals(e))
    children.foreach(_.remove(e))
    e.parent = None
  }

  /**
   * Adds an entity to the container and sets parent/child relationships as necessary.
   *
   * @param e Entity to add.
   * @return The added entity.
   */
  def add(e: Entity3D) = {
    children ::= e
    e.parent = Some(this)
    e.create(this)
    e
  }

  /**
   * Draws all attached children entities.
   *
   * @param batch Active SpriteBatch to use in drawing.
   */
  def renderChildren(implicit batch: ModelBatch, environment: Environment) {
    children.map(_.render(batch, environment))
  }

  /**
   * Updates all attached children.
   *
   * @param delta Time elapsed since the last update tick.
   */
  def updateChildren(delta: Float) {
    children.map(_.update(delta))
  }
}
