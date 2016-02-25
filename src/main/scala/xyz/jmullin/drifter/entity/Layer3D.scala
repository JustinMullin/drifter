package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.{DefaultShaderProvider, ShaderProvider}
import com.badlogic.gdx.graphics.g3d.{Environment, ModelBatch}
import com.badlogic.gdx.utils.viewport.ExtendViewport
import xyz.jmullin.drifter.enrich.RichGeometry._

/**
 * 3-dimensional layer implementation.  Contains Entity3Ds.
 *
 * @param viewportSize Size of the viewport to use in drawing the world.
 */
class Layer3D(val viewportSize: V2, fov: Float = 67f, shaderProvider: ShaderProvider = new DefaultShaderProvider) extends EntityContainer3D with Layer {

  // Self reference for containership
  def layer = Some(this)

  /**
   * Camera used to render the world.
   */
  var camera = new PerspectiveCamera(fov, viewportSize.x, viewportSize.y)

  /**
   * Viewport for world-space rendering.
   */
  viewport = new ExtendViewport(viewportSize.x, viewportSize.y, camera)

  /**
   * Main model batch.  This will be passed to children and will generally be used to render
   * any model drawn for this layer.
   */
  implicit val batch = new ModelBatch(shaderProvider)

  /**
   * 3D environment for light/shadow config.
   */
  implicit val environment = new Environment()
  environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.0f))

  // Set camera parameters and run an initial update tick.
  camera.position.set(V3(1, 1, 1))
  camera.lookAt(0, 0, 0)
  camera.near = 0.1f
  camera.far = 1000f
  camera.update()

  /**
   * Given another layer, links the camera and viewport instances for those layers, syncing pan/zoom/etc.
   * This can simplify the setup of two parallel layers.
   *
   * @param layer Layer to link to.
   */
  def linkTo(layer: Layer3D): Unit = {
    camera = layer.camera
    viewport = layer.viewport
  }

  /**
   * Updates this layer and any children (if the layer is active).
   *
   * @param delta Time elapsed in seconds since the last update tick.
   */
  override def update(delta: Float): Unit = {
    if(active) {
      camera.update()
      updateChildren(delta)
    }
  }

  /**
   * Draws this layer and any children (if the layer is visible).
   */
  def render(): Unit = {
    if(visible) {
      camera.update()

      batch.begin(camera)
      renderChildren(batch, environment)
      batch.end()
    }
  }

  override def resize(newSize: V2): Unit = viewport.update(newSize.x.toInt, newSize.y.toInt, false)

  /**
   * Dispose layer resources.
   */
  override def dispose(): Unit = {
    batch.dispose()
  }
}