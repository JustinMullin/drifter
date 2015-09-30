package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g3d.Renderable
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider

class DrifterShaderProvider extends BaseShaderProvider {
  override def createShader(renderable: Renderable) = {
    new DrifterShader3D(renderable)
  }
}
