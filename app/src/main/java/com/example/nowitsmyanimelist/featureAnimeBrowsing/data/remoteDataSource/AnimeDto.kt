package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource

import android.provider.MediaStore
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GraphQlRequest(
    val query: String
)

@Serializable
data class GraphQlResponse<T>(
    val data: T?,
    val errors: List<GraphQlError>? = null
)

@Serializable
data class GraphQlError(
    val message: String
)

@Serializable
data class PageResponse(
    val Page: Page?
)
@Serializable
data class Page(
    val media: List<AnimeDto>?
)

@Serializable
@SerialName("Anime")
data class AnimeDto(
    val id: Int,
    val title: Titles,
    val description: String?,
    val episodes: Int?,
    val isAdult: Boolean,
    val trending: Int?,
    val genres: List<String>?,
    val meanScore: Int?,
    val status: String,
    val studios: StudioConnection?,
    val coverImage: MediaCoverImage,
)

@Serializable
data class Titles(
    val romaji: String?
)

@Serializable
data class MediaCoverImage(
    val medium: String?
)

@Serializable
data class StudioConnection(
    val nodes: List<Studio>?
)

@Serializable
data class Studio(
    val name: String?
)

fun AnimeDto.toAnime(): Anime {
    return Anime(
        id = this.id,
        title = this.title.romaji ?: "Unknown",
        description = this.description ?: "",
        episodes = this.episodes ?: 0,
        isAdult = this.isAdult,
        trending = this.trending ?: 0,
        genres = this.genres ?: emptyList(),
        meanScore = this.meanScore ?: 0,
        status = this.status,
        studios = this.studios?.nodes?.map { it.name ?: "Unknown" } ?: emptyList(),
        coverImage = this.coverImage.medium ?: "https://media.istockphoto.com/id/1980276924/vector/no-photo-thumbnail-graphic-element-no-found-or-available-image-in-the-gallery-or-album-flat.jpg?s=612x612&w=0&k=20&c=ZBE3NqfzIeHGDPkyvulUw14SaWfDj2rZtyiKv3toItk="
    )
}