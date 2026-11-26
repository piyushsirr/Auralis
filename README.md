# Auralis

**Auralis** is an open-source, liquid-glow music streaming app for Android, redesigned from the BeatWave codebase with a fresh visual identity, neon-glass aesthetic, and rebranded experience.

## Highlights

- **Liquid Glow UI** — vibrant neon-glass surfaces, glowing highlights, chromatic lens effects, and a deep dark palette tuned for cyan/purple/magenta accents.
- **Music Streaming** — YouTube Music streaming, background playback, playlists, search, lyrics, and offline downloads (inherited from the upstream project architecture).
- **Modern Android Stack** — Kotlin, Jetpack Compose, Material 3, Media3/ExoPlayer, Room, and DataStore.
- **Rebranded** — original BeatWave name/logo removed and replaced with the Auralis identity.

## What's Changed in This Redesign

- New **Auralis** name and app identity across manifest, resources, and package references.
- New liquid-glow app icon and launcher assets.
- Updated theme defaults: vivid glow accent (`#7B6CFF`), deep dark surfaces, vibrant Material color generation.
- Enhanced glass effect defaults: higher vibrancy, chromatic aberration, glowing highlight rim, and tuned lens/surface opacity for a liquid-glow look.
- Updated colors/resources for the new visual direction.

## Build

This is an Android Gradle project.

1. Open the project in **Android Studio** (latest stable recommended).
2. Ensure Android SDK / JDK requirements from the project are installed.
3. Sync Gradle and build:

```bash
./gradlew :app:assembleDebug
```

> Note: This repository was redesigned in an agent workspace; build it locally in Android Studio/CI with the Android SDK to produce an APK.

## Project Structure

- `app/` — main Android app (Compose UI, playback, data, settings)
- `innertube/` — YouTube Music/InnerTube client module
- other modules — lyrics, canvas, updater, and supporting features from the upstream codebase

## Credits

Auralis is based on the open-source BeatWave/ViMusic/OuterTune lineage and related upstream projects. Please retain upstream licenses and attribution when distributing.

## License

See [LICENSE](LICENSE) for license details.