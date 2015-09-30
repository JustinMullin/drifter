package xyz.jmullin.drifter.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.{Music, Sound}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, TextureAtlas}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.reflect.{ClassReflection, Field}
import xyz.jmullin.drifter.animation.Animation

object DrifterAssets {
  val prefixMap = Map[Class[_], AssetType](
    classOf[Texture] -> AssetType("texture/", ".png"),
    classOf[BitmapFont] -> AssetType("font/", ".fnt"),
    classOf[Sound] -> AssetType("sound/", ".wav"),
    classOf[Music] -> AssetType("music/", ".ogg"),
    classOf[Skin] -> AssetType("skin/", ".json"),
    classOf[TextureAtlas] -> AssetType("atlas/", ".atlas")
  )
}

class DrifterAssets {
  import xyz.jmullin.drifter.assets.DrifterAssets._

  val manager = new AssetManager

  var atlas: Option[TextureAtlas] = None

  def fields = {
    ClassReflection.getDeclaredFields(this.getClass)
  }

  def load() {
    for(field <- fields) {
      if(prefixMap.contains(field.getType)) {
        manager.load(getAssetPath(field), field.getType)
      }
    }
  }

  def finishLoading() {
    manager.finishLoading()
  }

  def populate() {
    for(field <- fields) {
      if(prefixMap.contains(field.getType)) {
        val path = getAssetPath(field)

        field.setAccessible(true)
        field.set(this, manager.get(path, field.getType))

        if(field.getType == classOf[TextureAtlas]) {
          atlas = Some(manager.get(path, field.getType).asInstanceOf[TextureAtlas])
        }
      }
    }

    for(field <- fields) {
      if(field.getType == classOf[Sprite]) {
        if(atlas.isEmpty) {
          throw new RuntimeException(f"No texture atlas loaded to pull sprite '${field.getName}' from.  " +
            "Make sure you have a TextureAtlas asset annotated with PrimaryAtlas.")
        }

        field.setAccessible(true)
        field.set(this, atlas.get.createSprite(field.getName))
      }
      if(field.getType == classOf[Animation]) {
        val pattern = """([a-zA-Z]+)(\d+)""".r

        field.getName match {
          case pattern(baseName, frames) =>
            var sprites = Vector[Sprite]()

            for(i <- 1 to frames.toInt) {
              sprites :+= atlas.get.createSprite(baseName, i)
            }

            field.setAccessible(true)
            field.set(this, new Animation(sprites))
        }
      }
    }
  }

  def getAssetPath(field: Field) = {
    prefixMap(field.getType).pathPrefix + field.getName + prefixMap(field.getType).fileSuffix
  }

  def getAssetPath(t: Class[_], n: String) = {
    prefixMap(t).pathPrefix + n + prefixMap(t).fileSuffix
  }

  def dispose() {
    atlas.map(_.dispose)
  }
}