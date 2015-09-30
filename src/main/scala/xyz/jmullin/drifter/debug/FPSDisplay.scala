package xyz.jmullin.drifter.debug

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import xyz.jmullin.drifter.Draw
import xyz.jmullin.drifter.GdxAlias._
import xyz.jmullin.drifter.enrich.RichGeometry._
import xyz.jmullin.drifter.entity.Entity2D

class FPSDisplay(font: => BitmapFont, color: Color = Color.YELLOW, align: V2) extends Entity2D {
  override def render(implicit batch: SpriteBatch): Unit = {
    font.setColor(Color.YELLOW)
    Draw.string(f"FPS: $gameFps", align.inverse.max(0, 0)*gameSize + align*3, font, align)
  }
}
