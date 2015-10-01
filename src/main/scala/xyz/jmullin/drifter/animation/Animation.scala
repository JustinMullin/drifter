package xyz.jmullin.drifter.animation

import com.badlogic.gdx.graphics.g2d.Sprite

/**
 * Convenience collection of a set of sprites for use in sequential animation.
 */
case class Animation(sprites: Seq[Sprite]) {
  /**
   * Retrieves the proper sprite by alpha representing progress through the animation.
   *
   * @param a Alpha value 0-1 to be used in picking a sprite from the sequence.
   * @return The active sprite lerped from the specified alpha and sprite sequence.
   */
  def frame(a: Float) = sprites(math.min(sprites.length-1, a*sprites.length).toInt)
}
