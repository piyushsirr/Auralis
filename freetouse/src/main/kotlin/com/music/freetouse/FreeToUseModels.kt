package com.music.freetouse

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

// ---- Tuple helpers ----
// API returns artists/categories/tags as [sequenceNumber, value] arrays.

@Serializable
data class FreeToUseArtistRef(
    val id: String = "",
    val name: String = "",
)

@Serializable
data class FreeToUseCategoryRef(
    val id: String = "",
    val name: String = "",
)

object TrackArtistEntrySerializer : KSerializer<Pair<Int, FreeToUseArtistRef>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("TrackArtistEntry")
    override fun serialize(encoder: Encoder, value: Pair<Int, FreeToUseArtistRef>) {
        throw UnsupportedOperationException("Serialization not supported")
    }
    override fun deserialize(decoder: Decoder): Pair<Int, FreeToUseArtistRef> {
        val jsonDecoder = decoder as JsonDecoder
        val arr = jsonDecoder.decodeJsonElement() as JsonArray
        val seq = (arr.getOrNull(0) as? JsonPrimitive)?.intOrNull ?: 0
        val obj = arr.getOrNull(1) as? JsonObject
        val ref = if (obj != null) jsonDecoder.json.decodeFromJsonElement(FreeToUseArtistRef.serializer(), obj)
                  else FreeToUseArtistRef()
        return seq to ref
    }
}

object TrackCategoryEntrySerializer : KSerializer<Pair<Int, FreeToUseCategoryRef>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("TrackCategoryEntry")
    override fun serialize(encoder: Encoder, value: Pair<Int, FreeToUseCategoryRef>) {
        throw UnsupportedOperationException("Serialization not supported")
    }
    override fun deserialize(decoder: Decoder): Pair<Int, FreeToUseCategoryRef> {
        val jsonDecoder = decoder as JsonDecoder
        val arr = jsonDecoder.decodeJsonElement() as JsonArray
        val seq = (arr.getOrNull(0) as? JsonPrimitive)?.intOrNull ?: 0
        val obj = arr.getOrNull(1) as? JsonObject
        val ref = if (obj != null) jsonDecoder.json.decodeFromJsonElement(FreeToUseCategoryRef.serializer(), obj)
                  else FreeToUseCategoryRef()
        return seq to ref
    }
}

object TrackTagEntrySerializer : KSerializer<Pair<Int, String>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("TrackTagEntry")
    override fun serialize(encoder: Encoder, value: Pair<Int, String>) {
        throw UnsupportedOperationException("Serialization not supported")
    }
    override fun deserialize(decoder: Decoder): Pair<Int, String> {
        val jsonDecoder = decoder as JsonDecoder
        val arr = jsonDecoder.decodeJsonElement() as JsonArray
        val seq = (arr.getOrNull(0) as? JsonPrimitive)?.intOrNull ?: 0
        val tag = (arr.getOrNull(1) as? JsonPrimitive)?.contentOrNull ?: ""
        return seq to tag
    }
}

@Serializable
data class FreeToUseThumbnails(
    val sm: String = "",
    val md: String = "",
    val lg: String = "",
    val xl: String = "",
) {
    fun bestUrl(): String = xl.ifBlank { lg.ifBlank { md.ifBlank { sm } } }
}

@Serializable
data class FreeToUseFiles(
    val mp3: String = "",
)

@Serializable
data class FreeToUseTrack(
    val id: String = "",
    val title: String = "",
    @Serializable(with = TrackArtistListSerializer::class)
    val artists: List<Pair<Int, FreeToUseArtistRef>> = emptyList(),
    val genre: String? = null,
    val lyrics: String? = null,
    val status: Int = 1,
    @SerialName("record_label") val recordLabel: String = "",
    @SerialName("is_premium") val isPremium: Boolean = false,
    val duration: Double = 0.0,
    @SerialName("release_date") val releaseDate: String = "",
    val waveform: List<Int> = emptyList(),
    @SerialName("staff_order") val staffOrder: Int = 0,
    val views: Int = 0,
    val plays: Int = 0,
    val likes: Int = 0,
    val downloads: Int = 0,
    @SerialName("update_time") val updateTime: String = "",
    @Serializable(with = TrackTagListSerializer::class)
    val tags: List<Pair<Int, String>> = emptyList(),
    @Serializable(with = TrackCategoryListSerializer::class)
    val categories: List<Pair<Int, FreeToUseCategoryRef>> = emptyList(),
    val thumbnails: FreeToUseThumbnails = FreeToUseThumbnails(),
    val files: FreeToUseFiles = FreeToUseFiles(),
    val similarity: Int? = null,
) {
    fun artistNames(): List<String> = artists.sortedBy { it.first }.map { it.second.name }.filter { it.isNotBlank() }
    fun primaryArtistName(): String = artistNames().firstOrNull().orEmpty()
    fun artistDisplay(): String = artistNames().joinToString(", ")
    fun streamUrl(): String = files.mp3
    fun thumbnailUrl(): String = thumbnails.bestUrl()
    fun durationMs(): Long = (duration * 1000).toLong()
}

object TrackArtistListSerializer : KSerializer<List<Pair<Int, FreeToUseArtistRef>>> {
    private val delegate = ListSerializer(TrackArtistEntrySerializer)
    override val descriptor: SerialDescriptor = delegate.descriptor
    override fun serialize(encoder: Encoder, value: List<Pair<Int, FreeToUseArtistRef>>) = delegate.serialize(encoder, value)
    override fun deserialize(decoder: Decoder): List<Pair<Int, FreeToUseArtistRef>> = delegate.deserialize(decoder)
}

object TrackCategoryListSerializer : KSerializer<List<Pair<Int, FreeToUseCategoryRef>>> {
    private val delegate = ListSerializer(TrackCategoryEntrySerializer)
    override val descriptor: SerialDescriptor = delegate.descriptor
    override fun serialize(encoder: Encoder, value: List<Pair<Int, FreeToUseCategoryRef>>) = delegate.serialize(encoder, value)
    override fun deserialize(decoder: Decoder): List<Pair<Int, FreeToUseCategoryRef>> = delegate.deserialize(decoder)
}

object TrackTagListSerializer : KSerializer<List<Pair<Int, String>>> {
    private val delegate = ListSerializer(TrackTagEntrySerializer)
    override val descriptor: SerialDescriptor = delegate.descriptor
    override fun serialize(encoder: Encoder, value: List<Pair<Int, String>>) = delegate.serialize(encoder, value)
    override fun deserialize(decoder: Decoder): List<Pair<Int, String>> = delegate.deserialize(decoder)
}

@Serializable
data class FreeToUseArtist(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val status: Int = 1,
    val views: Int = 0,
    @SerialName("update_time") val updateTime: String = "",
    @SerialName("upload_date") val uploadDate: String = "",
    val thumbnails: FreeToUseThumbnails = FreeToUseThumbnails(),
)

@Serializable
data class FreeToUseCategory(
    val id: String = "",
    val type: String = "",
    @SerialName("type_id") val typeId: String = "",
    val name: String = "",
    val description: String = "",
    val thumbnails: FreeToUseThumbnails = FreeToUseThumbnails(),
)

@Serializable
data class FreeToUsePagination(
    val limit: Int? = null,
    val offset: Int = 0,
    val count: Int = 0,
)

@Serializable
data class FreeToUseTracksResponse(
    val ok: Boolean = false,
    val data: List<FreeToUseTrack> = emptyList(),
    val pagination: FreeToUsePagination = FreeToUsePagination(),
)

@Serializable
data class FreeToUseTrackResponse(
    val ok: Boolean = false,
    val data: FreeToUseTrack? = null,
)

@Serializable
data class FreeToUseArtistsResponse(
    val ok: Boolean = false,
    val data: List<FreeToUseArtist> = emptyList(),
    val pagination: FreeToUsePagination = FreeToUsePagination(),
)

@Serializable
data class FreeToUseCategoriesResponse(
    val ok: Boolean = false,
    val data: List<FreeToUseCategory> = emptyList(),
    val pagination: FreeToUsePagination = FreeToUsePagination(),
)

@Serializable
data class FreeToUseErrorResponse(
    val ok: Boolean = false,
    val error: String = "",
)
