/**
 * Auralis Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.auralis.music.lyrics

import android.content.Context
import com.auralis.music.betterlyrics.BetterLyrics
import com.auralis.music.constants.EnableBetterLyricsKey
import com.auralis.music.utils.dataStore
import com.auralis.music.utils.get

object BetterLyricsProvider : LyricsProvider {
    override val name = "BetterLyrics"

    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableBetterLyricsKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
    ): Result<String> = BetterLyrics.getLyrics(title, artist, duration, album)

    override suspend fun getAllLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
        callback: (String) -> Unit,
    ) {
        BetterLyrics.getAllLyrics(title, artist, duration, album, callback)
    }
}
