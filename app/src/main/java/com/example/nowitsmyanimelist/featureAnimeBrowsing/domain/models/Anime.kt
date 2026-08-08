package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models

import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import kotlinx.serialization.Serializable

data class Anime(
    val id: Int,
    val title: String,
    val description: String,
    val episodes: Int,
    val isAdult: Boolean,
    val trending: Int,
    val genres: List<String>,
    val meanScore: Int,
    val status: String,
    val studios: List<String>,
    val coverImage: String,
)
