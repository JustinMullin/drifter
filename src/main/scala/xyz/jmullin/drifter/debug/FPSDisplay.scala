package xyz.jmullin.drifter.debug

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import xyz.jmullin.drifter.Draw
import xyz.jmullin.drifter.GdxAlias._
import xyz.jmullin.drifter.enrich.RichGeometry._
import xyz.jmullin.drifter.entity.Entity2D

/**
 * Simple FPS debug display.
 *
 * @param font Font used to draw FPS counter.
 * @param color Color used to draw FPS counter.
 * @param align Alignment of FPS counter on the screen - if align is (1, 1), for example, FPS will be drawn
 *              aligned at the top-right of the screen.  At (0, 0) it will be drawn at the lower left.
 */
class FPSDisplay(font: => BitmapFont, color: Color = Color.YELLOW, align: V2 = V2(0, 1)) extends Entity2D {
  override def render(implicit batch: SpriteBatch): Unit = {
    font.setColor(Color.YELLOW)
    Draw.string(f"FPS: $gameFps", align.max(0, 0)*gameSize + align.inverse*3, font, align.inverse)
  }
}
