package xyz.jmullin.drifter.animation

import xyz.jmullin.drifter.FloatMath._

/**
 * Defines an easing function for interpolation between 0-1.
 *
 * @param interpolate Function to use in interpolating; takes a linear alpha and returns the interpolated alpha.
 */
class Easing(val interpolate: Float => Float) {
  def apply(n: Float) = interpolate(n)
}

/**
 * Commonly used easing functions.
 */
object Easing {
  object Linear extends Easing(n => n)
  object Sine extends Easing(n => (1 + sin((n-0.5f)*Pi)) / 2f)
}