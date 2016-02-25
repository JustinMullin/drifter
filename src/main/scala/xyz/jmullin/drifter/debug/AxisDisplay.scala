package xyz.jmullin.drifter.debug

import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.{Environment, ModelBatch, ModelInstance, Material}
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import xyz.jmullin.drifter.entity.Entity3D

/**
 * Draws a debug axis display to orient models in 3d space
 *
 * @param gridMin Min unit coordinate to draw grid lines at
 * @param gridMax Max unit coordinate to draw grid lines at
 * @param gridStep Size of each grid cell
 * @param axisLength Distance to draw axis segments from origin
 */
class AxisDisplay(axisLength: Option[Float] = Some(100f), gridMin: Float = -10f, gridMax: Float = 10f, gridStep: Float = 1f) extends Entity3D {

  val modelBuilder = new ModelBuilder()
  modelBuilder.begin()

  val gridBuilder = modelBuilder.part("grid", GL20.GL_LINES, Usage.Position | Usage.ColorUnpacked, new Material())
  gridBuilder.setColor(Color.LIGHT_GRAY)

  for (t <- gridMin to (gridMax, gridStep)) {
    gridBuilder.line(t, 0, gridMin, t, 0, gridMax)
    gridBuilder.line(gridMin, 0, t, gridMax, 0, t)
  }

  axisLength map { axisLength =>
    val axisBuilder = modelBuilder.part("axes", GL20.GL_LINES, Usage.Position | Usage.ColorUnpacked, new Material())
    axisBuilder.setColor(Color.RED)
    axisBuilder.line(0, 0, 0, axisLength, 0, 0)
    axisBuilder.setColor(Color.GREEN)
    axisBuilder.line(0, 0, 0, 0, axisLength, 0)
    axisBuilder.setColor(Color.BLUE)
    axisBuilder.line(0, 0, 0, 0, 0, axisLength)
  }

  val model = modelBuilder.end()

  val instance = new ModelInstance(model)

  override def render(implicit batch: ModelBatch, environment: Environment): Unit = {
    batch.render(instance)

    super.render
  }
}
