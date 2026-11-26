package com.auralis.music.freetouse

import com.music.freetouse.FreeToUseArtist
import com.music.freetouse.FreeToUseCategory
import com.music.freetouse.FreeToUseService
import com.music.freetouse.FreeToUseTrack

/**
 * Thin app-side repository for the Free To Use public music API.
 * Provides browse/search/stream URL access for royalty-free tracks.
 */
object FreeToUseRepository {

    suspend fun trending(limit: Int = 20, offset: Int = 0): Result<List<FreeToUseTrack>> =
        FreeToUseService.getTracks(limit = limit, offset = offset, order = "plays", sort = "desc")

    suspend fun latest(limit: Int = 20, offset: Int = 0): Result<List<FreeToUseTrack>> =
        FreeToUseService.getTracks(limit = limit, offset = offset, order = "release_date", sort = "desc")

    suspend fun search(query: String, limit: Int = 20, offset: Int = 0): Result<List<FreeToUseTrack>> =
        FreeToUseService.searchTracks(query = query, limit = limit, offset = offset)

    suspend fun track(id: String): Result<FreeToUseTrack?> =
        FreeToUseService.getTrack(id)

    suspend fun related(id: String, limit: Int = 20): Result<List<FreeToUseTrack>> =
        FreeToUseService.getRelatedTracks(id = id, limit = limit)

    suspend fun artists(limit: Int = 20, offset: Int = 0): Result<List<FreeToUseArtist>> =
        FreeToUseService.getArtists(limit = limit, offset = offset)

    suspend fun categories(limit: Int = 20, offset: Int = 0): Result<List<FreeToUseCategory>> =
        FreeToUseService.getCategories(limit = limit, offset = offset)

    suspend fun categoryTracks(categoryId: String, limit: Int = 20, offset: Int = 0): Result<List<FreeToUseTrack>> =
        FreeToUseService.getCategoryTracks(categoryId = categoryId, limit = limit, offset = offset)

    fun streamUrl(track: FreeToUseTrack): String? = track.files.mp3.takeIf { it.isNotBlank() }
}
