package xyz.jmullin.drifter

import xyz.jmullin.drifter.enrich.RichColor._
import xyz.jmullin.drifter.enrich.RichGeometry._

import scala.util.Random

/**
 * Convenience methods for getting simple randomized results. Random functions require an implicit
 * [[scala.util.Random]] instance. This allows for a fixed seed if repeatable random behavior is desirable.
 * A global [[Random]] instance with a nanotime seed is available at RandomUtil.Implicits.global if you prefer
 * not to supply your own.
 */
object RandomUtil {
  object Implicits {
    val global = new Random()
  }

  def probability(p: Double)(implicit r: Random) = r.nextDouble() <= p
  def rInt(n: Int)(implicit r: Random) = r.nextInt(n)
  def rInt(n:Int, m:Int)(implicit r: Random) = if(n == m) n else n+r.nextInt(m-n)
  def rFloat(n: Float)(implicit r: Random) = r.nextFloat()*n
  def rFloat(n: Float, m: Float)(implicit r: Random) = n+r.nextFloat()*(m-n)
  def rElement[T](s: Iterable[T])(implicit r: Random) = r.shuffle(s).head

  def rV(v: V2)(implicit r: Random) = V2(rFloat(v.x)(r), rFloat(v.y)(r))
  def rV(a: V2, b: V2)(implicit r: Random) = V2(rFloat(a.x, b.x)(r), rFloat(a.y, b.y)(r))
  def rV(r: Rect)(implicit ra: Random): V2 = rV(r.v, r.size)(ra)
  def rV(v: V3)(implicit r: Random) = V3(rFloat(v.x)(r), rFloat(v.y)(r), rFloat(v.z)(r))
  def rV(a: V3, b: V3)(implicit r: Random) = V3(rFloat(a.x, b.x)(r), rFloat(a.y, b.y)(r), rFloat(a.z, b.z)(r))
  def rColor(n: Float, m: Float)(implicit r: Random) = C(rFloat(n, m)(r), rFloat(n, m)(r), rFloat(n, m)(r))

  def shuffle[T](seq: Seq[T])(implicit r: Random) = r.shuffle(seq)
}
