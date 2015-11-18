package xyz.jmullin.drifter

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.graphics.glutils.{ShaderProgram, ShapeRenderer}
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.utils.Align
import xyz.jmullin.drifter.enrich.RichGeometry._
import xyz.jmullin.drifter.entity.Layer2D

/**
 * Convenience methods for drawing things succinctly.
 */
object Draw {
  /**
   * Shared GlyphLayout for calculating text display.  Very not thread safe!
   */
  var layout = new GlyphLayout()

  /**
   * Draw a sprite at a given position and size.
   */
  def sprite(sprite: Sprite, v: V2, size: V2)(implicit batch: Batch): Unit = {
    sprite.setBounds(v.x, v.y, size.x, size.y)
    sprite.draw(batch)
  }

  /**
   * Draw a nine-patch at a given position and size.
   */
  def sprite(patch: NinePatch, v: V2, size: V2)(implicit batch: Batch): Unit = {
    patch.draw(batch, v.x, v.y, size.x, size.y)
  }

  /**
   * Draw a texture at a given position and size.
   */
  def texture(texture: Texture, v: V2, size: V2)(implicit batch: Batch): Unit = {
    batch.draw(texture, v.x, v.y, size.x, size.y)
  }

  /**
   * Given a [[ShapeRenderer.ShapeType]] to draw and a Unit block, provides an active [[ShapeRenderer]] with
   * basic parameters setup to simplify the process of drawing primitives to the screen.
   *
   * '''Example:'''
   *
   * {{{
   * Draw.shapes(ShapeType.Filled) { r =>
   *   r.rect(0, 0, 100, 100)
   * }
   * }}}
   */
  def shapes(kind: ShapeRenderer.ShapeType, shader: ShaderProgram=Shaders.default.program)(f: ShapeRenderer => Unit)(implicit layer: Layer2D, batch: Batch): Unit = {
    batch.end()
    Gdx.gl.glEnable(GL20.GL_BLEND)
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    val r = new ShapeRenderer(5000, shader)
    r.begin(kind)
    r.setProjectionMatrix(layer.camera.combined)

    f(r)

    r.end()
    r.dispose()
    Gdx.gl.glDisable(GL20.GL_BLEND)
    batch.begin()
  }

  /**
   * Draw a string with the given location, font and alignment.
   */
  def string(str: String, v: V2, font: BitmapFont, align: V2)(implicit batch: Batch): Unit = {
    layout.setText(font, str)
    font.draw(batch, str, v.x + (align.x - 1) * layout.width * 0.5f, v.y + (align.y + 1) * layout.height * 0.5f)
  }

  /**
   * Draw a string with the given location, font and alignment, wrapped to the specified width.
   */
  def stringWrapped(str: String, v: V2, width: Float, font: BitmapFont, align: V2)(implicit batch: Batch): Unit = {
    layout.setText(font, str, font.getColor, width, Align.center, true)
    font.draw(batch, str, v.x - layout.width / 2f, v.y + (align.y + 1) * layout.height * 0.5f)
  }
}
