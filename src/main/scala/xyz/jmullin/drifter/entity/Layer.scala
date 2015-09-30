package xyz.jmullin.drifter.entity

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.utils.viewport.Viewport
import xyz.jmullin.drifter.enrich.RichGeometry
import RichGeometry._

trait Layer extends InputProcessor {
  val viewportSize: V2
  val autoCenter: Boolean
  var viewport: Viewport = _

  var visible = true
  var active = true

  def update(delta: Float): Unit
  def render(): Unit
  def dispose(): Unit = {

  }
}