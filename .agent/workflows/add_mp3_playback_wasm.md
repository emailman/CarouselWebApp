---
description: How to add MP3 playback to a Compose Multiplatform (Wasm) project
---

# Add MP3 Playback (Wasm/Js)

This workflow outlines how to add simple audio playback capabilities to a Kotlin Compose Multiplatform project targeting Wasm/JS.

## 1. Create Expect Class (CommonMain)

Create `composeApp/src/commonMain/kotlin/SoundPlayer.kt`:

```kotlin
expect class SoundPlayer(fileName: String) {
    fun play()
    fun pause()
    fun stop()
    fun dispose()
}
```

## 2. Create Actual Class (WasmJsMain)

Create `composeApp/src/wasmJsMain/kotlin/SoundPlayer.wasmJs.kt` using the HTML5 Audio API:

```kotlin
import org.w3c.dom.Audio

actual class SoundPlayer actual constructor(fileName: String) {
    private val audio = Audio(fileName)

    init {
        // Set to loop by default, or expose as parameter
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
}
```

## 3. Add Resource File

Place your MP3 file (e.g., `carousel.mp3`) in the resources directory:
`composeApp/src/wasmJsMain/resources/carousel.mp3`

## 4. Usage in Composable

```kotlin
@Composable
fun AudioAwareComponent() {
    val soundPlayer = remember { SoundPlayer("carousel.mp3") }
    
    DisposableEffect(Unit) {
        onDispose { soundPlayer.dispose() }
    }
    
    Button(onClick = { soundPlayer.play() }) {
        Text("Play Sound")
    }
}
```
