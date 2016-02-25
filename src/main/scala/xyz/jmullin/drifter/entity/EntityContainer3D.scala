package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g3d.{Environment, ModelBatch}
import xyz.jmullin.drifter.DrifterInput
import xyz.jmullin.drifter.enrich.RichGeometry._

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
    children.foreach(_.render(batch, environment))
  }

  /**
   * Updates all attached children.
   *
   * @param delta Time elapsed since the last update tick.
   */
  def updateChildren(delta: Float) {
    children.foreach(_.update(delta))
  }

  // Input events are aggregated through this container's children and coalesced to a single hit result.

  def mouseEvent(v: V2, event: (Entity3D, V3) => Boolean): Boolean = {
    (for(camera <- layer.map(_.camera)) yield {
      val hits = children.flatMap(e => e.hitPoint(camera.position, camera.direction).map(e -> _))
      val closest = hits.sortBy { case (entity, hit) => (camera.position - hit).len() }
      closest.exists(event.tupled)
    }).getOrElse(false)
  }

  override def touchDown(v: V2, pointer: Int, button: Int) = mouseEvent(v, _.touchDown(_, button))
  override def touchUp(v: V2, pointer: Int, button: Int) = mouseEvent(v, _.touchUp(_, button))
  override def touchDragged(v: V2, pointer: Int) = mouseEvent(v, _.touchDragged(_))
  override def mouseMoved(v: V2) = mouseEvent(v, _.mouseMoved(_))

  override def keyDown(keycode: Int): Boolean = {
    val hit = children.find(_.keyDown(keycode))
    hit.isDefined
  }

  override def keyUp(keycode: Int): Boolean = {
    val hit = children.find(_.keyUp(keycode))
    hit.isDefined
  }

  override def keyTyped(character: Char): Boolean = {
    val hit = children.find(_.keyTyped(character))
    hit.isDefined
  }

  override def scrolled(amount: Int): Boolean = {
    val hit = children.find(_.scrolled(amount))
    hit.isDefined
  }
}
