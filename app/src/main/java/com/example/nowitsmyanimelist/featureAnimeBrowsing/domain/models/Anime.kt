package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models

import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import kotlinx.serialization.Serializable


@Serializable
data class GraphQlRequest(
    val query: String
)

@Serializable
data class GraphQlResponse<T>(
    val data: T?
)

@Serializable
data class PageResponse(
    val Page: Page?
)
@Serializable
data class Page(
    val media: List<Anime>?
)

@Serializable
data class Anime(
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
