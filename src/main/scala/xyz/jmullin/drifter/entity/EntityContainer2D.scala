package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import xyz.jmullin.drifter.DrifterInput
import xyz.jmullin.drifter.enrich.RichGeometry
import RichGeometry._

trait EntityContainer2D extends DrifterInput {
  implicit def layer: Option[Layer2D]
  var children = List[Entity2D]()

  def remove(e: Entity2D): Unit = {
    children = children.filterNot(_.equals(e))
    children.foreach(_.remove(e))
    e.parent = None
  }

  def add(e: Entity2D) = {
    children ::= e
    e.parent = Some(this)
    e.create(this)
    e
  }

  def renderChildren(batch: SpriteBatch) {
    children = children.sortBy(-_.depth)

    children.map(_.render(batch))
  }

  def updateChildren(delta: Float) {
    children.map(_.update(delta))
  }

  override def touchDown(v: V2, pointer: Int, button: Int): Boolean = {
    val hit = children.find(e => e.containsPoint(v) && e.touchDown(v, pointer, button))
    hit.isDefined
  }

  override def touchUp(v: V2, pointer: Int, button: Int): Boolean = {
    val hit = children.find(e => e.containsPoint(v) && e.touchUp(v, pointer, button))
    hit.isDefined
  }

  override def touchDragged(v: V2, pointer: Int): Boolean = {
    val hit = children.find(e => e.containsPoint(v) && e.touchDragged(v, pointer))
    hit.isDefined
  }

  override def mouseMoved(v: V2): Boolean = {
    val hit = children.find(e => e.containsPoint(v) && e.mouseMoved(v))
    hit.isDefined
  }

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
