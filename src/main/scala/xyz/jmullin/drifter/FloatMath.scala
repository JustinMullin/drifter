package xyz.jmullin.drifter

/**
 * Convenience methods for doing core math stuffs on floats without needing
 * to cast back and forth. Includes aliases for some already-float functions to reduce the
 * need for two imports.
 */
object FloatMath {
  val Pi = math.Pi.toFloat

  def abs(n: Float) = math.abs(n)

  def cos(n: Float) = math.cos(n).toFloat
  def sin(n: Float) = math.sin(n).toFloat
  def tan(n: Float) = math.tan(n).toFloat
  def asin(n: Float) = math.asin(n).toFloat
  def acos(n: Float) = math.acos(n).toFloat
  def atan(n: Float) = math.atan(n).toFloat
  def atan2(y: Float, x: Float) = math.atan2(y, x).toFloat

  def floor(n: Float) = math.floor(n).toFloat
  def ceil(n: Float) = math.ceil(n).toFloat
  def round(n: Float) = math.round(n).toFloat
  def min(m: Float, n: Float) = math.min(m, n)
  def max(m: Float, n: Float) = math.max(m, n)
  def clamp(n: Float, a: Float, b: Float) = math.max(a, math.min(b, n))

  def degToRad(deg: Float) = deg * Pi / 180f
  def radToDeg(rad: Float) = rad * 180f / Pi

  def sign(n: Float) = math.signum(n)

  def pow(m: Float, n: Float) = math.pow(m, n).toFloat
  def sqrt(n: Float) = math.sqrt(n).toFloat
}
