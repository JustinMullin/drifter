package xyz.jmullin.drifter

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram

/**
 * Given files to load shader definitions from, compiles and wraps a [[ShaderProgram]] and functionality
 * for reloading the program.  Define init() and tick() methods to set shader uniforms.
 *
 * @param fragmentShaderName Filename of the fragment shader to load.
 * @param vertexShaderName Filename of the vertex shader to load.
 */
class ShaderSet(fragmentShaderName: String, vertexShaderName: String) {
  val vert = Gdx.files.internal(f"shader/$vertexShaderName.vert")
  val frag = Gdx.files.internal(f"shader/$fragmentShaderName.frag")

  /**
   * System ms time at which this shader was last compiled.
   */
  var lastCompileTime = 0l

  /**
   * The loaded shader program.
   */
  var program: ShaderProgram = _

  compile()

  /**
   * Compile the shader program from the specified source.
   */
  def compile(): Unit = {
    program = new ShaderProgram(vert, frag)
    lastCompileTime = System.currentTimeMillis()

    if(!program.isCompiled) {
      println(program.getLog)
    }
  }

  /**
   * Reload the shader from source; can be used to do live shader edits at runtime.
   */
  def refresh(): Unit = {
    if(vert.lastModified() > lastCompileTime) {
      compile()
      println(f"Reloaded shader $fragmentShaderName / $vertexShaderName.")
    }
  }

  /**
   * Extend to set shader parameters at creation time.
   */
  def init(): Unit = {}

  /**
   * Extend to set shader parameters on a tick-by-tick basis.
   */
  def tick(): Unit = {}
}
