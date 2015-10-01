package xyz.jmullin.drifter.enrich

import com.badlogic.gdx.math.Rectangle
import xyz.jmullin.drifter.enrich.RichGeometry._

/**
 * Enriched [[Rectangle]] providing convenience methods for manipulation.
 * @param r Wrapped [[Rectangle]] instance to enrich.
 */
class RichRectangle(r: Rect) {
  def v = V2(r.x, r.y)
  def size = V2(r.width, r.height)
}
