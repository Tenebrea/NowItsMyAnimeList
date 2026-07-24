package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark(
    @PrimaryKey
    val id: Long,
    val bookmark: String?,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)

