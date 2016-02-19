package xyz.jmullin.drifter.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.{Music, Sound}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, TextureAtlas}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.reflect.{ClassReflection, Field}
import xyz.jmullin.drifter.animation.Animation

/**
 * Used for loading and storing an assets library.
 * 
 * '''To use:'''
 * 
 * Create a class or object which extends from DrifterAssets, and define one field per asset to load.
 * Assets will be loaded from the classpath based on field type, using the class mapping defined in
 * [[DrifterAssets.PrefixMap]] to determine the relative path and file extension to load from.  File names
 * must match the field name specified except in the case of Animation, which takes a suffix indicating the
 * number of frames to load and looks for all files named with that pattern from 1 to N.
 *
 * Call load() to begin loading assets.  To perform inline loading, consult the manager member (see [[AssetManager]] for
 * more information), otherwise simply call finishLoading() to force the application to block until load is complete.
 *
 * After load is completed, call populate() to dump the loaded assets into their respective fields.  After this
 * point these fields can be used to reference assets.
 */
class DrifterAssets {
  import xyz.jmullin.drifter.assets.DrifterAssets._

  val manager = new AssetManager

  var atlas: Option[TextureAtlas] = None

  /**
   * Retrieves a list of fields from ''this''.
   */
  def fields = {
    ClassReflection.getDeclaredFields(this.getClass)
  }

  /**
   * Triggers loading of all assets defined in ''this''.
   */
  def load() {
    for(field <- fields) {
      if(PrefixMap.contains(field.getType)) {
        manager.load(getAssetPath(field), field.getType)
      }
    }
  }

  /**
   * Forces the asset manager to complete load of any in-process assets.
   */
  def finishLoading() {
    manager.finishLoading()
  }

  /**
   * Populates the fields of ''this'' with the loaded assets.
   */
  def populate() {
    for(field <- fields) {
      if(PrefixMap.contains(field.getType)) {
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
          throw new RuntimeException(f"No texture atlas loaded to pull sprite '${field.getName}' from.  At least one TextureAtlas is required to load Sprites.")
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
    PrefixMap(field.getType).pathPrefix + field.getName + PrefixMap(field.getType).fileSuffix
  }

  def getAssetPath(t: Class[_], n: String) = {
    PrefixMap(t).pathPrefix + n + PrefixMap(t).fileSuffix
  }

  def dispose() {
    atlas.map(_.dispose)
  }
}

object DrifterAssets {
  // These placeholders can be used to simplify the task of specifying types in an Assets object.
  // NOTE: These return null, and should only be used in the context of an Assets object which has yet
  // to be auto-populated!
  def texture: Texture = null
  def font: BitmapFont = null
  def sound: Sound = null
  def music: Music = null
  def skin: Skin = null
  def atlas: TextureAtlas = null

  /**
   * Map of classes to asset types.  Currently the assumption is a single type of asset will always correspond
   * to a single well-defined path and file extension.
   */
  val PrefixMap = Map[Class[_], AssetType](
    classOf[Texture] -> AssetType("texture/", ".png"),
    classOf[BitmapFont] -> AssetType("font/", ".fnt"),
    classOf[Sound] -> AssetType("sound/", ".wav"),
    classOf[Music] -> AssetType("music/", ".ogg"),
    classOf[Skin] -> AssetType("skin/", ".json"),
    classOf[TextureAtlas] -> AssetType("atlas/", ".atlas")
  )
}