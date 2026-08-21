package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories

import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getFavorites(): Flow<List<Bookmark>>
    fun getWatching(): Flow<List<Bookmark>>
    fun getPlanned(): Flow<List<Bookmark>>
    fun getAlreadyWatched(): Flow<List<Bookmark>>
    fun getDelayed(): Flow<List<Bookmark>>
    fun getAbandoned(): Flow<List<Bookmark>>
    // Запись в Room асинхронна; suspend не позволяет выполнить блокирующую работу в главном потоке.
    suspend fun updateBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    fun getBookmarks(): Flow<List<Bookmark>>
    suspend fun getBookmarkById(animeId: Int): Bookmark?
}
