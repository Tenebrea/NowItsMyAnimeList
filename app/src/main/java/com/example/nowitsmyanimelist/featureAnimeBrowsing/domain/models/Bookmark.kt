package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmark",
    // У одного аниме может быть только одно состояние закладки; прежний self-FK этого не обеспечивал.
    indices = [Index(value = ["anime_id"], unique = true)]
)
data class Bookmark(
    @PrimaryKey
    val id: Long,
    val bookmark: String?,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "anime_id")
    val animeId: Int
)

