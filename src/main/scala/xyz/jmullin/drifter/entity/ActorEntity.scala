package xyz.jmullin.drifter.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Actor

class ActorEntity(a: Actor) extends Entity2D {
  override def create(container: EntityContainer2D): Unit = {

  }

  override def update(implicit delta: Float): Unit = {
    a.act(delta)
  }

  override def render(implicit batch: SpriteBatch): Unit = {
    a.draw(batch, 1)
  }
}
