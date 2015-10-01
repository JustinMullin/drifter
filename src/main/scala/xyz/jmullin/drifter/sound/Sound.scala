package xyz.jmullin.drifter.sound

import com.badlogic.gdx.audio.{Music, Sound => GdxSound}

/**
 * Very simple convenience object used to wrap Sound and Music play methods to allow an "off switch"
 * for sound and music calls.
 */
object Sound {
  var sfxOn = true
  var musicOn = true

  /**
   * Play a sound (if sfx enabled).
   *
   * @param s Sound to play.
   * @param v Volume (0-1) to play the sound at.
   * @return The played Sound.
   */
  def play(s: GdxSound, v: Float=1f) = {
    if(sfxOn) s.play(v)
    s
  }

  /**
   * Start a looping music track.
   *
   * @param m Music to play.
   * @return The played Music.
   */
  def play(m: Music) = {
    m.setLooping(true)
    m.setVolume(1)
    if(musicOn) m.play()
    m
  }
}
