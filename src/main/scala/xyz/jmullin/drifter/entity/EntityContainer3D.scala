package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g3d.{Environment, ModelBatch}
import xyz.jmullin.drifter.DrifterInput

trait EntityContainer3D extends DrifterInput {
  implicit def layer: Option[Layer3D]
  var children = List[Entity3D]()

  def remove(e: Entity3D): Unit = {
    children = children.filterNot(_.equals(e))
    children.foreach(_.remove(e))
    e.parent = None
  }

  def add(e: Entity3D) = {
    children ::= e
    e.parent = Some(this)
    e.create(this)
    e
  }

  def renderChildren(implicit batch: ModelBatch, environment: Environment) {
    children.map(_.render(batch, environment))
  }

  def updateChildren(delta: Float) {
    children.map(_.update(delta))
  }
}
