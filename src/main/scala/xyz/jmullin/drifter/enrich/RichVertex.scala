package xyz.jmullin.drifter.enrich

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo
import xyz.jmullin.drifter.enrich.RichGeometry._

object RichVertex {
  def vx(v: V3, normal: V3, color: Color, uv: V2) = new VertexInfo().setPos(v).setNor(normal).setCol(color).setUV(uv)

  implicit def enrichVertex(v: VertexInfo) = new RichVertex(v)
}

class RichVertex(v: VertexInfo) {
  
}
