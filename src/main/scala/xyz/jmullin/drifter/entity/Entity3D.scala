package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g3d.{Environment, ModelBatch}
import xyz.jmullin.drifter.enrich.RichGeometry._

trait Entity3D extends EntityContainer3D with Entity {
  implicit def self = this

  implicit def layer = parent.flatMap(_.layer)
  var parent: Option[EntityContainer3D] = None

  var position = V3(0, 0, 0)

  def dead = parent.isEmpty

  var hovered = false

  def create(container: EntityContainer3D): Unit = {
    parent = Some(container)
  }

  def render(implicit batch: ModelBatch, environment: Environment): Unit = {
    renderChildren(batch, environment)
  }

  override def update(implicit delta: Float): Unit = {
    updateChildren(delta)

    super.update(delta)
  }

  def remove(): Unit = {
    layer.map(_.remove(this))
    parent = None
  }
}