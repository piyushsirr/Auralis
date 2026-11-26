package com.auralis.music.freetouse

import com.auralis.music.models.MediaMetadata
import com.music.freetouse.FreeToUseTrack

/**
 * Maps Free To Use API tracks into Auralis media models.
 */
fun FreeToUseTrack.toMediaMetadata(): MediaMetadata =
    MediaMetadata(
        id = "ftu:$id",
        title = title,
        artists = artistNames().map { name ->
            MediaMetadata.Artist(id = null, name = name)
        }.ifEmpty { listOf(MediaMetadata.Artist(id = null, name = "Unknown Artist")) },
        duration = duration.toInt().coerceAtLeast(0),
        thumbnailUrl = thumbnailUrl().ifBlank { null },
        explicit = false,
    )

fun FreeToUseTrack.isStreamable(): Boolean = files.mp3.isNotBlank()
