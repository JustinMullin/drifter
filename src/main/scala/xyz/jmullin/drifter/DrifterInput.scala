package xyz.jmullin.drifter

import com.badlogic.gdx.InputAdapter
import xyz.jmullin.drifter.enrich.RichGeometry
import RichGeometry._

/**
 * Input adapter that wraps x,y pairs into vectors.
 */
class DrifterInput extends InputAdapter {
  final override def touchDown(x: Int, y: Int, pointer: Int, button: Int) = touchDown(V2(x, y), pointer, button)
  final override def touchUp(x: Int, y: Int, pointer: Int, button: Int) = touchUp(V2(x, y), pointer, button)
  final override def touchDragged(x: Int, y: Int, pointer: Int) = {
    touchDragged(V2(x, y), pointer)
    mouseMoved(V2(x, y))
  }
  final override def mouseMoved(x: Int, y: Int) = mouseMoved(V2(x, y))

  def touchDown(v: V2, pointer: Int, button: Int) = false
  def touchUp(v: V2, pointer: Int, button: Int) = false
  def touchDragged(v: V2, pointer: Int) = false
  def mouseMoved(v: V2) = false
}
