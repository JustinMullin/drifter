package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import xyz.jmullin.drifter.enrich.RichGeometry
import RichGeometry._

trait Entity2D extends EntityContainer2D with Entity {
  implicit def self = this

  implicit def layer = parent.flatMap(_.layer)
  var parent: Option[EntityContainer2D] = None

  var position = V2(0, 0)
  var size = V2(0, 0)
  var rotation = 0
  var depth = 0

  def dead = parent.isEmpty

  var hovered = false

  def x = position.x
  def x_=(x: Float) { position.x = x }
  def y = position.y
  def y_=(y: Float) { position.y = y }
  def width = size.x
  def width_=(width: Float) { size.x = width }
  def height = size.y
  def height_=(height: Float) { size.y = height }

  val _bounds = new Rectangle
  def bounds = _bounds.set(x, y, width, height)

  def create(container: EntityContainer2D): Unit = {
    parent = Some(container)
  }

  def render(implicit batch: SpriteBatch): Unit = {
    renderChildren(batch)
  }

  def remove(): Unit = {
    layer.map(_.remove(this))
    parent = None
  }

  def replaceWith(e: Entity2D): Unit = {
    layer.map { l =>
      l.add(e)
      e.position.set(position)
      e.size.set(size)
      remove()
    }
  }

  def containsPoint(v: V2): Boolean = bounds.contains(v) || children.exists(_.containsPoint(v))

  def hover(v: V2): Boolean = {
    val hit = children.find(_.hover(v))
    if(hit.isDefined) {
      hovered = true
      true
    } else {
      if(containsPoint(v)) {
        hovered = true
        true
      } else {
        false
      }
    }
  }

  def clearHover(): Unit ={
    hovered = false
    children.foreach(_.clearHover())
  }

  override def touchDown(v: V2, pointer: Int, button: Int): Boolean = super.touchDown(v, pointer, button)
  override def touchUp(v: V2, pointer: Int, button: Int): Boolean = super.touchUp(v, pointer, button)
  override def touchDragged(v: V2, pointer: Int): Boolean = super.touchDragged(v, pointer)
  override def mouseMoved(v: V2): Boolean = super.mouseMoved(v)

  def unproject(v: V2) = layer.map(_.viewport.unproject(v.cpy())).getOrElse(V2(0, 0))
  def unproject(x: Float, y: Float) = layer.map(_.viewport.unproject(V2(x, y))).getOrElse(V2(0, 0))
}