package xyz.jmullin.drifter.enrich

import xyz.jmullin.drifter.enrich.RichGeometry._

/**
 * Enriched [[Vector2]] providing convenience methods for manipulation.
 * @param v Wrapped [[Vector2]] instance to enrich.
 */
class RichVector2(v: Vector2) {
  def +(o: V2) = v.cpy().add(o)
  def +(n: Float) = v.cpy().add(n, n)
  def -(o: V2) = v.cpy().sub(o)
  def -(n: Float) = v.cpy().sub(n, n)
  def *(o: V2) = v.cpy().scl(o)
  def *(n: Float) = v.cpy().scl(n, n)
  def *(m: M3) = v.cpy().mul(m)
  def /(o: V2) = v.cpy().scl(1f/o.x, 1f/o.y)
  def /(n: Float) = v.cpy().scl(1f/n, 1f/n)

  def abs = V2(math.abs(v.x), math.abs(v.y))
  def inverse = (v * -1).fixZeroes
  def flipX = (v * V2(-1, 1)).fixZeroes
  def flipY = (v * V2(1, -1)).fixZeroes

  def center(o: V2) = v + o/2f
  def midpoint(o: V2) = V2((v.x-o.x)*0.5f, (v.y-o.y)*0.5f)

  def min(x: Float, y: Float) = V2(math.min(v.x, x), math.min(v.y, y))
  def max(x: Float, y: Float) = V2(math.max(v.x, x), math.max(v.y, y))
  def floor = V2(math.floor(v.x).toFloat, math.floor(v.y).toFloat).fixZeroes
  def ceil = V2(math.ceil(v.x).toFloat, math.ceil(v.y).toFloat).fixZeroes
  def round = V2(math.round(v.x).toFloat, math.round(v.y).toFloat).fixZeroes

  def snap(scale: Float=1f) = V2(math.floor(v.x/scale).toFloat, math.floor(v.y/scale).toFloat)

  def neighbors = for(x <- -1 to 1; y <- -1 to 1; if x != 0 || y != 0) yield v + V2(x, y)
  def orthogonal = for(x <- -1 to 1; y <- -1 to 1; if math.abs(x)+math.abs(y) == 1) yield v + V2(x, y)

  /**
   * Gets rid of negative zero situations for reasons of equality. This is almost certainly bad 'cause
   * we shouldn't be naively comparing floats anyway...
   *
   * @return The vector with negative zero components replaced with positive zero
   */
  def fixZeroes = V2(if(v.x == 0.0f) 0.0f else v.x, if(v.y == 0.0f) 0.0f else v.y)

  def minComponent = math.min(math.abs(v.x), math.abs(v.y))
  def maxComponent = math.max(math.abs(v.x), math.abs(v.y))

  def manhattanTo(b: V2) = {
    val difference = (b-v).abs
    difference.x + difference.y
  }

  // SWIZZLING

  def xx = V2(v.x, v.x)
  def yy = V2(v.y, v.y)

  def xxx = V3(v.x, v.x, v.x)
  def xxy = V3(v.x, v.x, v.y)
  def xxo = V3(v.x, v.x, 0  )
  def xyx = V3(v.x, v.y, v.x)
  def xyy = V3(v.x, v.y, v.y)
  def xyo = V3(v.x, v.y, 0  )
  def xox = V3(v.x, 0  , v.x)
  def xoy = V3(v.x, 0  , v.y)
  def xoo = V3(v.x, 0  , 0  )
  def yxx = V3(v.y, v.x, v.x)
  def yxy = V3(v.y, v.x, v.y)
  def yxo = V3(v.y, v.x, 0  )
  def yyx = V3(v.y, v.y, v.x)
  def yyy = V3(v.y, v.y, v.y)
  def yyo = V3(v.y, v.y, 0  )
  def yox = V3(v.y, 0  , v.x)
  def yoy = V3(v.y, 0  , v.y)
  def yoo = V3(v.y, 0  , 0  )
  def oxx = V3(0  , v.x, v.x)
  def oxy = V3(0  , v.x, v.y)
  def oxo = V3(0  , v.x, 0  )
  def oyx = V3(0  , v.y, v.x)
  def oyy = V3(0  , v.y, v.y)
  def oyo = V3(0  , v.y, 0  )
  def oox = V3(0  , 0  , v.x)
  def ooy = V3(0  , 0  , v.y)
}
