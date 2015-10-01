package xyz.jmullin.drifter.enrich

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import RichGeometry._
import scala.language.implicitConversions

/**
 * Enriched [[ShapeRenderer]] providing convenience methods for manipulation.
 * @param r Wrapped [[ShapeRenderer]] instance to enrich.
 */
class RichShapeRenderer(r: ShapeRenderer) {
  def circle(v: V2, radius: Float): Unit = r.circle(v.x, v.y, radius)
  def circle(v: V2, radius: Float, n: Int): Unit = r.circle(v.x, v.y, radius, n)
}

object RichShapeRenderer {
  implicit def enrichShapeRenderer(r: ShapeRenderer) = new RichShapeRenderer(r)
}