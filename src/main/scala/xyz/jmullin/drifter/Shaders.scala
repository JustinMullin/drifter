package xyz.jmullin.drifter

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import xyz.jmullin.drifter.enrich.RichGeometry._
import GdxAlias._

object Shaders {
  private var _current = default
  def current = _current

  ShaderProgram.pedantic = false

  lazy val default = new ShaderSet("default", "default")

  lazy val df = new ShaderSet("df", "default") {
    var time = 0f

    override def init(): Unit = {
      program.setUniformf("gameSize", gameSize)
    }

    override def tick(): Unit = {
      time += Gdx.graphics.getDeltaTime
      program.setUniformf("time", time)
    }
  }

  lazy val ball = new ShaderSet("default", "default") {
    var time = 0f

    override def init(): Unit = {
      program.setUniformf("lightPosition", V3(1, 2, 1))
    }

    override def tick(): Unit = {
      time += Gdx.graphics.getDeltaTime
    }
  }

  def switch(s: ShaderSet)(implicit batch: SpriteBatch): Unit = {
    _current = s
    batch.flush()
    batch.setShader(s.program)

    s.refresh()
    s.init()
    s.tick()
  }
}
