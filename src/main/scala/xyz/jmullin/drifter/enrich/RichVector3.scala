package xyz.jmullin.drifter.enrich

import RichGeometry._

/**
 * Enriched [[Vector3]] providing convenience methods for manipulation.
 * @param v Wrapped [[Vector3]] instance to enrich.
 */
class RichVector3(v: V3) {
  def +(o: V3) = v.cpy().add(o)
  def +(n: Float) = v.cpy().add(n, n, n)
  def -(o: V3) = v.cpy().sub(o)
  def -(n: Float) = v.cpy().sub(n, n, n)
  def *(o: V3) = v.cpy().scl(o)
  def *(n: Float) = v.cpy().scl(n, n, n)
  def *(m: M3) = v.cpy().mul(m)
  def *(m: M4) = v.cpy().mul(m)
  def /(o: V3) = v.cpy().scl(1f/o.x, 1f/o.y, 1f/o.z)
  def /(n: Float) = v.cpy().scl(1f/n, 1f/n, 1f/n)

  def abs = V3(math.abs(v.x), math.abs(v.y), math.abs(v.z))
  def inverse = (v * -1).fixZeroes
  def fixZeroes = V3(if(v.x == 0.0f) 0.0f else v.x, if(v.y == 0.0f) 0.0f else v.y, if(v.z == 0.0f) 0.0f else v.z)

  def center(o: V3) = v + o/2f
  def midpoint(o: V3) = V3((v.x-o.x)*0.5f, (v.y-o.y)*0.5f, (v.z-o.z)*0.5f)

  def manhattanTo(b: V3) = {
    val difference = (b-v).abs
    difference.x + difference.y + difference.z
  }

  // SWIZZLING

  def xxx = V3(v.x, v.x, v.x)
  def xxy = V3(v.x, v.x, v.y)
  def xxz = V3(v.x, v.x, v.z)
  def xyx = V3(v.x, v.y, v.x)
  def xyy = V3(v.x, v.y, v.y)
  def xyz = V3(v.x, v.y, v.z)
  def xzx = V3(v.x, v.z, v.x)
  def xzy = V3(v.x, v.z, v.y)
  def xzz = V3(v.x, v.z, v.z)
  def yxx = V3(v.y, v.x, v.x)
  def yxy = V3(v.y, v.x, v.y)
  def yxz = V3(v.y, v.x, v.z)
  def yyx = V3(v.y, v.y, v.x)
  def yyy = V3(v.y, v.y, v.y)
  def yyz = V3(v.y, v.y, v.z)
  def yzx = V3(v.y, v.z, v.x)
  def yzy = V3(v.y, v.z, v.y)
  def yzz = V3(v.y, v.z, v.z)
  def zxx = V3(v.z, v.x, v.x)
  def zxy = V3(v.z, v.x, v.y)
  def zxz = V3(v.z, v.x, v.z)
  def zyx = V3(v.z, v.y, v.x)
  def zyy = V3(v.z, v.y, v.y)
  def zyz = V3(v.z, v.y, v.z)
  def zzx = V3(v.z, v.z, v.x)
  def zzy = V3(v.z, v.z, v.y)
  def zzz = V3(v.z, v.z, v.z)

  def xx = V2(v.x, v.x)
  def xy = V2(v.x, v.y)
  def xz = V2(v.x, v.z)
  def yx = V2(v.y, v.x)
  def yy = V2(v.y, v.y)
  def yz = V2(v.y, v.z)
  def zx = V2(v.z, v.x)
  def zy = V2(v.z, v.y)
  def zz = V2(v.z, v.z)
}
