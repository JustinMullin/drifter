package xyz.jmullin.drifter

import xyz.jmullin.drifter.enrich.RichColor._
import xyz.jmullin.drifter.enrich.RichGeometry._

import scala.util.Random

/**
 * Convenience methods for getting simple randomized results.
 */
object RandomUtil {
  lazy val rand = new Random(System.currentTimeMillis())

  def probability(p: Double) = rand.nextDouble() <= p
  def rInt(n: Int) = rand.nextInt(n)
  def rInt(n:Int, m:Int) = if(n == m) n else n+rand.nextInt(m-n)
  def rFloat(n: Float) = rand.nextFloat()*n
  def rFloat(n: Float, m: Float) = n+rand.nextFloat()*(m-n)
  def rElement[T](s: Iterable[T]) = rand.shuffle(s).head

  def rV(v: V2) = V2(rFloat(v.x), rFloat(v.y))
  def rV(a: V2, b: V2) = V2(rFloat(a.x, b.x), rFloat(a.y, b.y))
  def rV(r: Rect): V2 = rV(r.v, r.size)
  def rV(v: V3) = V3(rFloat(v.x), rFloat(v.y), rFloat(v.z))
  def rV(a: V3, b: V3) = V3(rFloat(a.x, b.x), rFloat(a.y, b.y), rFloat(a.z, b.z))
  def rColor(n: Float, m: Float) = C(rFloat(n, m), rFloat(n, m), rFloat(n, m))
}
