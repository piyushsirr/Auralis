/**
 * Auralis Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.auralis.music.playback.queues

import androidx.media3.common.MediaItem
import com.auralis.music.extensions.metadata
import com.auralis.music.models.MediaMetadata

class ListQueue(
    val title: String? = null,
    val items: List<MediaItem>,
    val startIndex: Int = 0,
    val position: Long = 0L,
    override val preloadItem: MediaMetadata? = items.getOrNull(startIndex)?.metadata,
) : Queue {

    override suspend fun getInitialStatus() = Queue.Status(title, items, startIndex, position)

    override fun hasNextPage(): Boolean = false

    override suspend fun nextPage() = throw UnsupportedOperationException()
}
