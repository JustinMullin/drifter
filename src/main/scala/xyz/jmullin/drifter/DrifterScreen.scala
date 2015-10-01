package xyz.jmullin.drifter

import com.badlogic.gdx._
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20._
import xyz.jmullin.drifter.GdxAlias._
import xyz.jmullin.drifter.enrich.RichGeometry._
import xyz.jmullin.drifter.entity.{Layer, Layer2D, Layer3D}

/**
 * Screen implementation for use with Drifter, facilitates creation and management of layers for
 * render/update of entities.
 *
 * @param background Background color to clear to on each frame.
 */
class DrifterScreen(background: Color=Color.BLACK) extends DrifterInput with Screen {
  /**
   * Layers attached to this screen.
   */
  var layers = List[Layer]()

  /**
   * Create and attach a new Layer2D to this screen.
   *
   * @param size Size of the new layer.
   * @param autoCenter If true, auto-center the layer's viewport.
   * @param shader If specified, the default shader for rendering this layer.
   * @return The created Layer.
   */
  def newLayer2D(size: V2, autoCenter: Boolean=false, shader: ShaderSet=Shaders.default) = {
    val layer = new Layer2D(size, autoCenter, shader)
    layers ::= layer
    layer
  }

  /**
   * Create and attach a new Layer3D to this screen.
   *
   * @param size Size of the new layer.
   * @param autoCenter If true, auto-center the layer's viewport.
   * @return The created Layer.
   */
  def newLayer3D(size: V2, autoCenter: Boolean=false) = {
    val layer = new Layer3D(size, autoCenter)
    layers ::= layer
    layer
  }

  /**
   * Renders the screen and all attached layers, clearing the screen and setting up default blending modes.
   *
   * @param delta Time elapsed since the last frame.
   */
  def render(delta: Float) {
    Gdx.gl.glClearColor(background.r, background.g, background.b, background.a)
    Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    Gdx.gl20.glEnable(GL_BLEND)
    Gdx.gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

    val delta = Gdx.graphics.getDeltaTime
    layers.foreach(_.update(delta))

    layers.foreach(_.render())
  }

  /**
   * Updates the viewports of attached layers when the screen is resized.
   *
   * @param width New width of the screen.
   * @param height New height of the screen.
   */
  def resize(width: Int, height: Int) {
    layers.foreach(l => l.viewport.update(width, height, l.autoCenter))
  }

  /**
   * Initializes the screen when shown.
   */
  def show() {
    resize(gameW, gameH)

    Gdx.input.setInputProcessor(new InputMultiplexer(this :: layers.reverse:_*))
  }

  /**
   * Dispose screen resources.
   */
  def dispose() {
    layers.foreach(_.dispose())
    layers = List()
  }

  def hide() {}
  def pause() {}
  def resume() {}
}