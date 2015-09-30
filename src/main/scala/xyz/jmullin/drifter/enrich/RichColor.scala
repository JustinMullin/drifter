package xyz.jmullin.drifter.enrich

import com.badlogic.gdx.graphics.Color
import RichColor._

object RichColor {
  type C = Color
  def C(r: Float, g: Float, b: Float) = new C(r, g, b, 1.0f)
  def Ca(r: Float, g: Float, b: Float, a: Float) = new C(r, g, b, a)
  def Ci(i: Float) = new C(i, i, i, 1.0f)

  implicit def enrichColor(c: C) = new RichColor(c)
  implicit def tupleToRichColor(t: (Float, Float, Float)) = C(t._1, t._2, t._3)
  implicit def tupleToRichColor(t: (Float, Float, Float, Float)) = Ca(t._1, t._2, t._3, t._4)
}

class RichColor(c: Color) {
  // Arithmetic by default does not modify alpha values
  def +(o: C) = c.cpy().add(o.alpha(0))
  def +(n: Float) = c.cpy().add(n, n, n, 0)
  def -(o: C) = c.cpy().sub(o.alpha(0))
  def -(n: Float) = c.cpy().sub(n, n, n, 0)
  def *(o: C) = c.cpy().mul(o.alpha(1))
  def *(n: Float) = c.cpy().mul(n, n, n, 1)
  def /(o: C) = c.cpy().mul(1f/o.r, 1f/o.g, 1f/o.b, 1f)
  def /(n: Float) = c.cpy().mul(1f/n, 1f/n, 1f/n, 1f)

  // Append an '!' to operate on alpha as well
  def +!(o: C) = c.cpy().add(o)
  def +!(n: Float) = c.cpy().add(n, n, n, n)
  def -!(o: C) = c.cpy().sub(o)
  def -!(n: Float) = c.cpy().sub(n, n, n, n)
  def *!(o: C) = c.cpy().mul(o)
  def *!(n: Float) = c.cpy().mul(n, n, n, n)
  def /!(o: C) = c.cpy().mul(1f/o.r, 1f/o.g, 1f/o.b, 1f/o.a)
  def /!(n: Float) = c.cpy().mul(1f/n, 1f/n, 1f/n, 1f/n)

  def alpha(a: Float) = c.cpy().set(c.r, c.g, c.b, 1f)
}
