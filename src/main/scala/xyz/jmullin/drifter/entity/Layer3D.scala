package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.{Environment, ModelBatch}
import com.badlogic.gdx.utils.viewport.ExtendViewport
import xyz.jmullin.drifter.enrich.RichGeometry
import RichGeometry._

class Layer3D(val viewportSize: V2, val autoCenter: Boolean) extends EntityContainer3D with Layer {
  def layer = Some(this)
  var camera = new PerspectiveCamera(45.0f, viewportSize.x, viewportSize.y)
  viewport = new ExtendViewport(viewportSize.x, viewportSize.y, camera)

  implicit val batch = new ModelBatch()//new DrifterShaderProvider)
  implicit val environment = new Environment()
  environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.0f))
  //environment.add(new PointLight().set(Color.WHITE, V3(3f, -4f, 2f), 5f))
  //environment.add(new PointLight().set(Color.WHITE, V3(-2f, 1f, -3f), 5f))
  //environment.add(new PointLight().set(Color.WHITE, V3(-1f, 3f, 1f), 5f))

  camera.near = 0.1f
  camera.far = 300f
  camera.update()

  def linkTo(layer: Layer3D): Unit = {
    camera = layer.camera
    viewport = layer.viewport
  }

  override def update(delta: Float): Unit = {
    if(active) {
      camera.update()
      updateChildren(delta)
    }
  }

  def render(): Unit = {
    if(visible) {
      camera.update()

      batch.begin(camera)
      renderChildren(batch, environment)
      batch.end()
    }
  }

  override def dispose(): Unit = {
    batch.dispose()
  }
}
