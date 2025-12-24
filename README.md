# Carousel

An interactive carousel (merry-go-round) simulation built with Kotlin Compose Multiplatform, targeting both JVM desktop and WebAssembly (browser).

## Features

- **Animated Carousel**: A spinning carousel with 16 colorful seats arranged in two concentric circles (8 outer, 8 inner)
- **Realistic Physics**: Smooth acceleration and deceleration during spin cycles
- **Sound Effects**: Carousel music with volume that adjusts dynamically based on spin speed
- **Interactive Controls**:
  - Start/Stop buttons
  - Adjustable number of revolutions (1-10)
  - Emergency stop capability

## Tech Stack

- **Kotlin Multiplatform** with Compose for UI
- **JVM Desktop** target with Compose Desktop and JavaFX for audio
- **WebAssembly (WASM)** target for browser deployment
- **Webpack** dev server for local development

## Prerequisites

- JDK 17 or higher
- Gradle 8.x

## Running Locally

### Desktop (JVM)

Run the desktop application:

```bash
./gradlew :composeApp:run
```

### Browser (WASM)

Start the development server with hot reload:

```bash
./gradlew wasmJsBrowserDevelopmentRun --continuous
```

The app will be available at `http://localhost:8080/` (or another port if 8080 is in use).

## Building for Production

Build the production bundle:

```bash
./gradlew wasmJsBrowserProductionWebpack
```

The output will be in `composeApp/build/dist/wasmJs/productionExecutable/`.

## Project Structure

```
Carousel/
├── composeApp/
│   └── src/
│       ├── commonMain/kotlin/
│       │   ├── App.kt              # Main carousel UI and animation logic
│       │   ├── CarouselModel.kt    # Data model for carousel seats
│       │   └── SoundPlayer.kt      # Sound player interface (expect)
│       ├── desktopMain/kotlin/
│       │   ├── main.kt             # Desktop entry point
│       │   └── SoundPlayer.desktop.kt  # JavaFX sound implementation
│       └── wasmJsMain/kotlin/
│           ├── main.kt             # WASM entry point
│           └── SoundPlayer.wasmJs.kt  # Web Audio sound implementation
├── build.gradle.kts
└── settings.gradle.kts
```

## License

MIT
