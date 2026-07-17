package com.example.nowitsmyanimelist.domain.repositories

import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.models.Anime
import com.example.nowitsmyanimelist.models.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getFavorites(): Flow<List<Bookmark>>
    fun getWatching(): Flow<List<Bookmark>>
    fun getPlanned(): Flow<List<Bookmark>>
    fun getAlreadyWatched(): Flow<List<Bookmark>>
    fun getDelayed(): Flow<List<Bookmark>>
    fun getAbandoned(): Flow<List<Bookmark>>
    fun addBookmark(id: Long, bookmarkType: BookmarkTypes)
    fun removeBookmark(id: Long)
    fun addFavorite(id: Long)
    fun removeFavorite(id: Long)
}