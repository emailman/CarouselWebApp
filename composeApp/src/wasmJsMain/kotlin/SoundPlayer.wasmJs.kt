import org.w3c.dom.Audio

actual class SoundPlayer actual constructor(fileName: String) {
    private val audio = Audio(fileName)

    init {
        audio.loop = true
    }

    actual fun play() {
        audio.play()
    }

    actual fun pause() {
        audio.pause()
    }

    actual fun stop() {
        audio.pause()
        audio.currentTime = 0.0
    }

    actual fun dispose() {
        audio.pause()
        audio.src = ""
    }

    actual fun setVolume(volume: Float) {
        audio.volume = volume.toDouble().coerceIn(0.0, 1.0)
    }
}
