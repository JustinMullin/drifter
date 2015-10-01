package xyz.jmullin.drifter.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20._
import com.badlogic.gdx.graphics.g3d.attributes._
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.{Uniform, Setter => SetterGDX}
import com.badlogic.gdx.graphics.g3d.{Attributes, Renderable, Shader}
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.{Color, GL20}
import xyz.jmullin.drifter.enrich.RichGeometry._

/**
 * Basic 3D LibGDX shader implementation in Scala, based on [[com.badlogic.gdx.graphics.g3d.shaders.DefaultShader]].
 */
class DrifterShader3D(var renderable: Renderable) extends BaseShader {
  import xyz.jmullin.drifter.entity.DrifterShader3D._

  register(cameraProjection)
  register(cameraView)
  register(cameraCombined)
  register(cameraPosition)
  register(cameraDirection)
  register(cameraUp)
  register(worldTransform)
  register(viewWorldTransform)
  register(combinedViewWorldTransform)
  register(normalMatrix)
  register(shininess)
  register(diffuseColor)
  register(diffuseTexture)
  register(specularColor)
  register(specularTexture)
  register(emissiveColor)
  register(reflectionColor)
  register(normalTexture)
  register(environmentCubemap)

  def register(s: Setter): Unit = {
    s.add(this)
  }

  override def init(): Unit = {
    init(new ShaderProgram(Gdx.files.internal("shader/vertex.glsl"), Gdx.files.internal("shader/fragment.glsl")), renderable)
  }

  override def render(renderable: Renderable): Unit = {
    context.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

    context.setCullFace(GL_BACK)
    context.setDepthTest(GL_LEQUAL, 0f, 1f)
    context.setDepthMask(true)

    super.render(renderable)
  }

  override def canRender(instance: Renderable) = true
  override def compareTo(other: Shader) = 0
}

object DrifterShader3D {
  val cameraProjection = setter("cameraProjection", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.camera.projection, true)

  val cameraView = setter("cameraView", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.camera.view, true)

  val cameraCombined = setter("cameraCombined", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.camera.combined, true)

  val cameraPosition = setter("cameraPosition", (s: BaseShader, r: Renderable, a: Attributes) => 
    (V3(s.camera.position.x, s.camera.position.y, s.camera.position.z), 1.1881f / (s.camera.far * s.camera.far)), true)

  val cameraDirection = setter("cameraDirection", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.camera.direction, true)

  val cameraUp = setter("cameraUp", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.camera.up, true)

  val worldTransform = setter[M4]("worldTransform", (s: BaseShader, r: Renderable, a: Attributes) => 
    r.worldTransform, false)

  val viewWorldTransform = setter[M4]("viewWorldTransform", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.camera.view.cpy().mul(r.worldTransform), false)

  val combinedViewWorldTransform = setter[M4]("combinedViewWorldTransform", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.camera.combined.cpy().mul(r.worldTransform), false)

  val normalMatrix = setter[M3]("normalMatrix", (s: BaseShader, r: Renderable, a: Attributes) => 
    new M3().set(r.worldTransform).inv().transpose(), false)

  val shininess = setter[Float]("shininess", (s: BaseShader, r: Renderable, a: Attributes) => 
    a.get(FloatAttribute.Shininess) match {case a: FloatAttribute => a.value; case _ => 12f}, false)

  val diffuseColor = setter[Color]("diffuseColor", (s: BaseShader, r: Renderable, a: Attributes) => 
    a.get(ColorAttribute.Diffuse) match { case a: ColorAttribute => a.color; case _ => Color.WHITE }, false)

  val diffuseTexture = setter[Int]("diffuseTexture", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.context.textureBinder.bind(a.get(TextureAttribute.Diffuse) match { case a: TextureAttribute => a.textureDescription }), false)

  val specularColor = setter[Color]("specularColor", (s: BaseShader, r: Renderable, a: Attributes) => 
    a.get(ColorAttribute.Specular) match { case a: ColorAttribute => a.color; case _ => Color.WHITE }, false)

  val specularTexture = setter[Int]("specularTexture", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.context.textureBinder.bind(a.get(TextureAttribute.Specular) match { case a: TextureAttribute => a.textureDescription }), false)

  val emissiveColor = setter[Color]("emissiveColor", (s: BaseShader, r: Renderable, a: Attributes) => 
    a.get(ColorAttribute.Emissive) match { case a: ColorAttribute => a.color; case _ => Color.WHITE }, false)

  val reflectionColor = setter[Color]("reflectionColor", (s: BaseShader, r: Renderable, a: Attributes) => 
    a.get(ColorAttribute.Reflection) match { case a: ColorAttribute => a.color; case _ => Color.WHITE }, false)

  val normalTexture = setter[Int]("normalTexture", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.context.textureBinder.bind(a.get(TextureAttribute.Normal) match { case a: TextureAttribute => a.textureDescription }), false)

  val environmentCubemap = setter[Int]("environmentCubemap", (s: BaseShader, r: Renderable, a: Attributes) => 
    s.context.textureBinder.bind(a.get(CubemapAttribute.EnvironmentMap) match { case a: CubemapAttribute => a.textureDescription }), false)

  abstract class Setter(val uniformName: String, global: Boolean) extends SetterGDX {
    def isGlobal(shader: BaseShader, inputID: Int) = global

    val uniform = new Uniform(uniformName)

    def add(shader: BaseShader): Unit = {
      shader.register(uniform, this)
    }
  }

  def setter[T](uniformName: String, f: (BaseShader, Renderable, Attributes) => T, global: Boolean) = new Setter(uniformName, global) {
    override def set(shader: BaseShader, inputID: Int, renderable: Renderable, attributes: Attributes): Unit = f(shader, renderable, attributes) match {
      case a: Float => shader.set(inputID, a)
      case a: Int => shader.set(inputID, a)
      case a: V2 => shader.set(inputID, a)
      case a: V3 => shader.set(inputID, a)
      case (v: V3, a: Float) => shader.set(inputID, v.x, v.y, v.z, a)
      case a: M3 => shader.set(inputID, a)
      case a: M4 => shader.set(inputID, a)
      case a: Color => shader.set(inputID, a)
      case a: Any => throw new Exception("No shader setter found for type " + a.getClass.getSimpleName)
    }
  }

  def setter[T](uniformName: String, v: T, global: Boolean): Setter = setter[T](uniformName, (s: BaseShader, r: Renderable, a: Attributes) => v, global)
}