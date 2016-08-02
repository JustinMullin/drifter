package xyz.jmullin.drifter

import xyz.jmullin.drifter.FloatMath._
import xyz.jmullin.drifter.enrich.RichGeometry._

case class VectorHex(q: Float, r: Float, s: Float) {
  assert(q + r + s == 0)

  def +(o: Vh) = VectorHex(q + o.q, r + o.r, s + o.s)
  def -(o: Vh) = VectorHex(q - o.q, r - o.r, s - o.s)
  def *(o: Vh) = VectorHex(q * o.q, r * o.r, s * o.s)
  def /(o: Vh) = VectorHex(q / o.q, r / o.r, s / o.s)

  def inverse = Vh(-q, -r, -s)
  def fixZeroes = Vh(if(q == 0.0f) 0.0f else q, if(r == 0.0f) 0.0f else r, if(s == 0.0f) 0.0f else s)

  def toV = V2(sqrt(3) * (q + r/2), 3/2 * r)

  def snap = {
    val (rQ, rR, rS) = (round(q), round(r), round(s))

    if (abs(rQ - q) > abs(rR - r) && abs(rQ - q) > abs(rS - s)) {
      VectorHex(-rR-rS, rR, rS)
    } else if (abs(rR - r) > abs(rS - s)) {
      VectorHex(rQ, -rQ-rS, rS)
    } else {
      VectorHex(rQ, rR, -rQ-rR)
    }
  }
}

object VectorHex {
  def apply(q: Float, s: Float): VectorHex = VectorHex(q, -q-s, s)
}