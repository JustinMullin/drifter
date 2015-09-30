package xyz.jmullin.drifter.enrich

import xyz.jmullin.drifter.enrich.RichGeometry._

class RichRectangle(r: Rect) {
  def v = V2(r.x, r.y)
  def size = V2(r.width, r.height)
}
