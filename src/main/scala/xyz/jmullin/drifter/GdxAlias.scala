package xyz.jmullin.drifter

import com.badlogic.gdx.Gdx
import xyz.jmullin.drifter.enrich.RichGeometry._

/**
 * Short aliases and convenience methods for interacting with Gdx globals.
 */
object GdxAlias {
  def mouseX = Gdx.input.getX
  def mouseY = gameH - Gdx.input.getY
  def mouseV = V2(mouseX, mouseY)
  def rawMouseV = V2(Gdx.input.getX, Gdx.input.getY)
  def mouseVelocity = V2(Gdx.input.getDeltaX, Gdx.input.getDeltaY)
  def gameW = Gdx.graphics.getWidth
  def gameH = Gdx.graphics.getHeight
  def gameSize = V2(gameW, gameH)
  def gameFps = Gdx.graphics.getFramesPerSecond
}
