package com.music.freetouse

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Free To Use public music API client.
 * API spec: https://api.freetouse.com/v3/openapi.json
 * No API key required.
 */
object FreeToUseService {

    private const val TAG = "FreeToUseService"
    private const val BASE_URL = "https://api.freetouse.com/v3/"

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
        coerceInputValues = true
    }

    private val client by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) { json(json) }
            install(HttpTimeout) {
                requestTimeoutMillis = 10_000
                connectTimeoutMillis = 5_000
                socketTimeoutMillis = 10_000
            }
            defaultRequest {
                url(BASE_URL)
                headers.append(HttpHeaders.Accept, "application/json")
                headers.append(HttpHeaders.UserAgent, "Auralis/1.0")
            }
            expectSuccess = false
        }
    }

    suspend fun getTracks(
        limit: Int = 20,
        offset: Int = 0,
        order: String = "release_date",
        sort: String = "desc",
    ): Result<List<FreeToUseTrack>> = runCatching {
        val response = client.get("music/tracks/all") {
            parameter("limit", limit)
            parameter("offset", offset)
            parameter("order", order)
            parameter("sort", sort)
        }
        if (response.status != HttpStatusCode.OK) {
            throw IllegalStateException("FreeToUse tracks failed: HTTP ${response.status.value}")
        }
        val body = response.body<FreeToUseTracksResponse>()
        if (!body.ok) throw IllegalStateException("FreeToUse tracks returned ok=false")
        body.data
    }.onFailure { Log.e(TAG, "getTracks failed", it) }

    suspend fun searchTracks(
        query: String,
        limit: Int = 20,
        offset: Int = 0,
        order: String = "release_date",
        sort: String = "desc",
    ): Result<List<FreeToUseTrack>> = runCatching {
        val response = client.get("music/tracks/search") {
            parameter("query", query)
            parameter("limit", limit)
            parameter("offset", offset)
            parameter("order", order)
            parameter("sort", sort)
        }
        if (response.status != HttpStatusCode.OK) {
            throw IllegalStateException("FreeToUse search failed: HTTP ${response.status.value}")
        }
        val body = response.body<FreeToUseTracksResponse>()
        if (!body.ok) throw IllegalStateException("FreeToUse search returned ok=false")
        body.data
    }.onFailure { Log.e(TAG, "searchTracks failed for query=$query", it) }

    suspend fun getTrack(id: String): Result<FreeToUseTrack?> = runCatching {
        val response = client.get("music/tracks/$id")
        if (response.status != HttpStatusCode.OK) {
            throw IllegalStateException("FreeToUse track failed: HTTP ${response.status.value}")
        }
        val body = response.body<FreeToUseTrackResponse>()
        if (!body.ok) throw IllegalStateException("FreeToUse track returned ok=false")
        body.data
    }.onFailure { Log.e(TAG, "getTrack failed for id=$id", it) }

    suspend fun getRelatedTracks(
        id: String,
        limit: Int = 20,
        offset: Int = 0,
    ): Result<List<FreeToUseTrack>> = runCatching {
        val response = client.get("music/tracks/$id/related") {
            parameter("limit", limit)
            parameter("offset", offset)
        }
        if (response.status != HttpStatusCode.OK) {
            throw IllegalStateException("FreeToUse related failed: HTTP ${response.status.value}")
        }
        val body = response.body<FreeToUseTracksResponse>()
        if (!body.ok) throw IllegalStateException("FreeToUse related returned ok=false")
        body.data
    }.onFailure { Log.e(TAG, "getRelatedTracks failed for id=$id", it) }

    suspend fun getArtists(
        limit: Int = 20,
        offset: Int = 0,
        order: String = "name",
        sort: String = "asc",
    ): Result<List<FreeToUseArtist>> = runCatching {
        val response = client.get("music/artists/all") {
            parameter("limit", limit)
            parameter("offset", offset)
            parameter("order", order)
            parameter("sort", sort)
        }
        if (response.status != HttpStatusCode.OK) {
            throw IllegalStateException("FreeToUse artists failed: HTTP ${response.status.value}")
        }
        val body = response.body<FreeToUseArtistsResponse>()
        if (!body.ok) throw IllegalStateException("FreeToUse artists returned ok=false")
        body.data
    }.onFailure { Log.e(TAG, "getArtists failed", it) }

    suspend fun getCategories(
        limit: Int = 20,
        offset: Int = 0,
    ): Result<List<FreeToUseCategory>> = runCatching {
        val response = client.get("music/categories/all") {
            parameter("limit", limit)
            parameter("offset", offset)
        }
        if (response.status != HttpStatusCode.OK) {
            throw IllegalStateException("FreeToUse categories failed: HTTP ${response.status.value}")
        }
        val body = response.body<FreeToUseCategoriesResponse>()
        if (!body.ok) throw IllegalStateException("FreeToUse categories returned ok=false")
        body.data
    }.onFailure { Log.e(TAG, "getCategories failed", it) }

    suspend fun getCategoryTracks(
        categoryId: String,
        limit: Int = 20,
        offset: Int = 0,
    ): Result<List<FreeToUseTrack>> = runCatching {
        val response = client.get("music/categories/$categoryId/tracks") {
            parameter("limit", limit)
            parameter("offset", offset)
        }
        if (response.status != HttpStatusCode.OK) {
            throw IllegalStateException("FreeToUse category tracks failed: HTTP ${response.status.value}")
        }
        val body = response.body<FreeToUseTracksResponse>()
        if (!body.ok) throw IllegalStateException("FreeToUse category tracks returned ok=false")
        body.data
    }.onFailure { Log.e(TAG, "getCategoryTracks failed for id=$categoryId", it) }
}
