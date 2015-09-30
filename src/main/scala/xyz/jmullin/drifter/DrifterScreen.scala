package xyz.jmullin.drifter

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20._
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.{Vector2, Vector3}
import com.badlogic.gdx.{Gdx, InputMultiplexer, Screen}
import xyz.jmullin.drifter.GdxAlias._
import xyz.jmullin.drifter.enrich.RichGeometry._
import xyz.jmullin.drifter.entity.{Layer, Layer2D, Layer3D}

abstract class DrifterScreen(game: DrifterGame, background: Color=Color.BLACK) extends DrifterInput with Screen {
  val shapeRenderer = new ShapeRenderer

  val size: Vector2 = new Vector2
  val mV: Vector3 = new Vector3
  val center: Vector2 = new Vector2

  var layers = List[Layer]()

  def newLayer2D(size: V2, autoCenter: Boolean=false, shader: ShaderSet=Shaders.default) = {
    val layer = new Layer2D(size, autoCenter, shader)
    layers ::= layer
    layer
  }

  def newLayer3D(size: V2, autoCenter: Boolean=false) = {
    val layer = new Layer3D(size, autoCenter)
    layers ::= layer
    layer
  }

  def render(delta: Float) {
    Gdx.gl.glClearColor(background.r, background.g, background.b, background.a)
    Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    Gdx.gl20.glEnable(GL_BLEND)
    Gdx.gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

    center.set(Gdx.graphics.getWidth/2.0f, Gdx.graphics.getHeight/2.0f)

    val delta = Gdx.graphics.getDeltaTime
    layers.foreach(_.update(delta))

    layers.foreach(_.render())
  }

  def resize(width: Int, height: Int) {
    size.set(width, height)
    layers.foreach(l => l.viewport.update(width, height, l.autoCenter))
  }

  def show() {
    resize(gameW, gameH)

    Gdx.input.setInputProcessor(new InputMultiplexer(this :: layers.reverse:_*))
  }

  override def mouseMoved(v: V2): Boolean = {
    super.mouseMoved(v)
  }

  def dispose() {
    layers.foreach(_.dispose())
    layers = List()
  }

  def hide() {}
  def pause() {}
  def resume() {}
}