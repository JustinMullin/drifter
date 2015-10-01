package xyz.jmullin.drifter

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import xyz.jmullin.drifter.entity.Layer2D

/**
 * Convenience object for shader switching.  Use switch in the context of a Layer2D to update
 * the current shader for that layer/batch and handle the context switching and flushing automatically.
 */
object Shaders {
  ShaderProgram.pedantic = false

  lazy val default = new ShaderSet("default", "default")

  def switch(s: ShaderSet)(implicit layer: Layer2D, batch: SpriteBatch): Unit = {
    layer.currentShader = s
    batch.flush()
    batch.setShader(s.program)

    s.refresh()
    s.init()
    s.tick()
  }
}
