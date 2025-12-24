import javafx.application.Platform
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer

actual class SoundPlayer actual constructor(fileName: String) {
    private val mediaPlayer: MediaPlayer

    init {
        initJavaFx()
        val resourceUrl = this::class.java.getResource("/$fileName")
            ?: throw IllegalArgumentException("Resource not found: $fileName")
        val media = Media(resourceUrl.toExternalForm())
        mediaPlayer = MediaPlayer(media).apply {
            cycleCount = MediaPlayer.INDEFINITE
        }
    }

    actual fun play() {
        mediaPlayer.play()
    }

    actual fun pause() {
        mediaPlayer.pause()
    }

    actual fun stop() {
        mediaPlayer.stop()
    }

    actual fun dispose() {
        mediaPlayer.dispose()
    }

    actual fun setVolume(volume: Float) {
        mediaPlayer.volume = volume.toDouble().coerceIn(0.0, 1.0)
    }

    companion object {
        private var javaFxInitialized = false

        @Synchronized
        private fun initJavaFx() {
            if (!javaFxInitialized) {
                Platform.startup {}
                javaFxInitialized = true
            }
        }
    }
}
