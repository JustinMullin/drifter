package xyz.jmullin.drifter.enrich

import com.badlogic.gdx.math.Rectangle
import xyz.jmullin.drifter.VectorHex
import scala.language.implicitConversions

/**
 * Convenience implicits, constructors and type aliases for working with enriched geometry classes.
 */
object RichGeometry {
  // 2d Vectors

  type Vector2 = com.badlogic.gdx.math.Vector2
  type V2 = Vector2
  def V2(xy: Float) = new V2(xy, xy)
  def V2(x: Float, y: Float) = new V2(x, y)

  implicit def enrichVector2(v: V2): RichVector2 = new RichVector2(v)
  implicit def tupleToRichVector2(t: (Float, Float)): V2 = V2(t._1, t._2)

  // 3d Vectors

  type Vector3 = com.badlogic.gdx.math.Vector3
  type V3 = Vector3
  def V3(xyz: Float) = new V3(xyz, xyz, xyz)
  def V3(v: V2, z: Float) = new V3(v.x, v.y, z)
  def V3(x: Float, v: V2) = new V3(x, v.x, v.y)
  def V3(x: Float, y: Float, z: Float) = new V3(x, y, z)

  implicit def enrichVector3(v: V3): RichVector3 = new RichVector3(v)
  implicit def tupleToRichVector3(t: (Float, Float, Float)): V3 = V3(t._1, t._2, t._3)

  // Matrices

  type M3 = com.badlogic.gdx.math.Matrix3
  type M4 = com.badlogic.gdx.math.Matrix4

  // Hex geometry

  type Vh = VectorHex
  def Vh(q: Float, r: Float, s: Float) = VectorHex(q, r, s)
  def Vh(q: Float, s: Float) = VectorHex(q, s)
  def Vho(x: Int, y: Int) = VectorHex(x, y - (x  - (x & 1)) / 2)

  // Other shapes

  type Rect = Rectangle
  def Rect(x: Float, y: Float, width: Float, height: Float) = new Rectangle(x, y, width, height)
  def Rect(v: V2, size: V2) = new Rectangle(v.x, v.y, size.x, size.y)

  implicit def enrichRect(r: Rect): RichRectangle = new RichRectangle(r)
}
