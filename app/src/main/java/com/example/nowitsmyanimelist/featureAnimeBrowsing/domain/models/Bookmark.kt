package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmark",
    foreignKeys = [
        ForeignKey(
            entity = Bookmark::class,
            parentColumns = arrayOf("anime_id"),
            childColumns = arrayOf("id"),
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
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

