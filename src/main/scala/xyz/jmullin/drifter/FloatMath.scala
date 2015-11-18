package xyz.jmullin.drifter

/**
 * Convenience methods for doing core math stuffs on floats without needing
 * to cast back and forth.
 */
object FloatMath {
  val Pi = math.Pi.toFloat

  def cos(n: Float) = math.cos(n).toFloat
  def sin(n: Float) = math.sin(n).toFloat
  def tan(n: Float) = math.tan(n).toFloat
  def asin(n: Float) = math.asin(n).toFloat
  def acos(n: Float) = math.acos(n).toFloat
  def atan(n: Float) = math.atan(n).toFloat
  def atan2(y: Float, x: Float) = math.atan2(y, x).toFloat

  def floor(n: Float) = math.floor(n).toFloat
  def ceil(n: Float) = math.ceil(n).toFloat
  def min(m: Float, n: Float) = math.min(m, n)
  def max(m: Float, n: Float) = math.max(m, n)
  def clamp(n: Float, a: Float, b: Float) = math.max(a, math.min(b, n))

  def pow(m: Float, n: Float) = math.pow(m, n).toFloat
}
