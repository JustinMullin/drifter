package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import xyz.jmullin.drifter.enrich.RichGeometry._
import xyz.jmullin.drifter.{ShaderSet, Shaders}

/**
 * 2-dimensional layer implementation.  Contains Entity2Ds.
 *
 * @param viewportSize Size of the viewport to use in drawing the world.
 * @param autoCenter If true, the viewport will be auto-centered in the world.
 * @param shader If specified, the [[ShaderSet]] to use by default in rendering sprites.
 */
class Layer2D(val viewportSize: V2, val autoCenter: Boolean, shader: ShaderSet=Shaders.default) extends EntityContainer2D with Layer {
  // Self reference for containership
  def layer = Some(this)

  /**
   * Camera used to render the world.
   */
  var camera = new OrthographicCamera(viewportSize.x, viewportSize.y)

  /**
   * Viewport for world-space rendering.
   */
  viewport = new ExtendViewport(viewportSize.x, viewportSize.y, camera)

  /**
   * Main sprite batch.  This will be passed to children and will generally be used to render
   * any sprite drawn for this layer.
   */
  implicit val batch = new SpriteBatch(1000, shader.program)

  /**
   * Current shader set on the sprite batch.
   */
  var currentShader = shader

  /**
   * Given another layer, links the camera and viewport instances for those layers, syncing pan/zoom/etc.
   * This can simplify the setup of two parallel layers.
   *
   * @param layer Layer to link to.
   */
  def linkTo(layer: Layer2D): Unit = {
    camera = layer.camera
    viewport = layer.viewport
  }

  /**
   * Draws this layer and any children (if the layer is visible).
   */
  override def render(): Unit = {
    if(visible) {
      batch.setProjectionMatrix(camera.combined)
      batch.begin()

      currentShader.tick()

      renderChildren(batch)

      batch.end()
      batch.flush()
    }
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

  override def touchDown(v: V2, pointer: Int, button: Int): Boolean = {
    // Ignore touch events if inactive
    if(!active) false else
    super.touchDown(unproject(v), pointer, button)
  }

  override def resize(newSize: V2): Unit = viewport.update(newSize.x.toInt, newSize.y.toInt, autoCenter)

  override def touchUp(v: V2, pointer: Int, button: Int): Boolean = {
    // Ignore touch events if inactive
    if(!active) false else
    super.touchUp(unproject(v), pointer, button)
  }

  override def mouseMoved(v: V2): Boolean = {
    // Ignore touch events if inactive
    if (!active) false else {
      val localV = unproject(v)
      super.mouseMoved(localV)
      children.exists(_.containsPoint(localV))
    }
  }

  /**
   * Unprojects a screen coordinate into the world space of the viewport.
   *
   * @param v Point to unproject.
   * @return The corresponding world space coordinate.
   */
  def unproject(v: V2) = viewport.unproject(v.cpy())
}