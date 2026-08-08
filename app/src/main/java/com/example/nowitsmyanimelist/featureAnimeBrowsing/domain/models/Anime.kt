package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import kotlinx.serialization.Serializable

@Entity(
    tableName = "anime_table",
)
data class Anime(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val episodes: Int,
    @ColumnInfo(name = "is_adult")
    val isAdult: Boolean,
    val trending: Int,
    val genres: List<String>,
    @ColumnInfo(name = "mean_score")
    val meanScore: Int,
    val status: String,
    val studios: List<String>,
    @ColumnInfo(name = "cover_image")
    val coverImage: String
)
