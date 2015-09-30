package xyz.jmullin.drifter

import com.badlogic.gdx.Gdx
import xyz.jmullin.drifter.enrich.RichGeometry._

object GdxAlias {
  def mouseX = Gdx.input.getX
  def mouseY = gameH - Gdx.input.getY-3
  def mouseV = V2(mouseX, mouseY)
  def rawMouseV = V2(Gdx.input.getX, Gdx.input.getY)
  def mouseVelocity = V2(Gdx.input.getDeltaX, Gdx.input.getDeltaY)
  def gameW = Gdx.graphics.getWidth
  def gameH = Gdx.graphics.getHeight
  def gameSize = V2(gameW, gameH)
  def gameFps = Gdx.graphics.getFramesPerSecond
}
