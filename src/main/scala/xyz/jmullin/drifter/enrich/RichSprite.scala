package xyz.jmullin.drifter.enrich

import com.badlogic.gdx.graphics.g2d.Sprite
import xyz.jmullin.drifter.enrich.RichGeometry._

object RichSprite {
  implicit def enrichSprite(sprite: Sprite): RichSprite = new RichSprite(sprite)
}

class RichSprite(sprite: Sprite) {
  def size = V2(sprite.getWidth, sprite.getHeight)
}
