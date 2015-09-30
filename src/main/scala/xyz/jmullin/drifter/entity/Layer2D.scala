package xyz.jmullin.drifter.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import xyz.jmullin.drifter.enrich.RichGeometry
import xyz.jmullin.drifter.{ShaderSet, Shaders}
import RichGeometry._

class Layer2D(val viewportSize: V2, val autoCenter: Boolean, shader: ShaderSet=Shaders.default) extends EntityContainer2D with Layer {
  def layer = Some(this)
  var camera = new OrthographicCamera(viewportSize.x, viewportSize.y)
  viewport = new ExtendViewport(viewportSize.x, viewportSize.y, camera)

  var time = 0f

  implicit var batch = new SpriteBatch(1000, shader.program)

  def linkTo(layer: Layer2D): Unit = {
    camera = layer.camera
    viewport = layer.viewport
  }

  override def render(): Unit = {
    if(visible) {
      batch.setProjectionMatrix(camera.combined)
      batch.begin()

      time += Gdx.graphics.getDeltaTime

      Shaders.current.tick()

      renderChildren(batch)

      batch.end()
      batch.flush()
    }
  }

  override def update(delta: Float): Unit = {
    if(active) {
      camera.update()
      updateChildren(delta)
    }
  }

  override def touchDown(v: V2, pointer: Int, button: Int): Boolean = {
    if(!active) false else
    super.touchDown(unproject(v), pointer, button)
  }

  override def touchUp(v: V2, pointer: Int, button: Int): Boolean = {
    if(!active) false else
    super.touchUp(unproject(v), pointer, button)
  }

  override def mouseMoved(v: V2): Boolean = {
    if (!active) false else {
      val hit = children.find(_.hover(unproject(v)))
      super.mouseMoved(unproject(v))
      hit.isDefined
    }
  }

  def clearHover(): Unit ={
    children.foreach(_.clearHover())
  }

  def unproject(v: V2) = viewport.unproject(v.cpy())
  def unproject(x: Float, y: Float) = viewport.unproject(V2(x, y))
}