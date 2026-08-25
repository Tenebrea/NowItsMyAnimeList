package com.example.nowitsmyanimelist.utils

import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookmarkRepositoryMock: BookmarkRepository {
    override fun getFavorites(): Flow<List<Bookmark>> {
        return flow { emit(emptyList()) }
    }

    override fun getWatching(): Flow<List<Bookmark>> {
        return flow { emit(emptyList()) }
    }

    override fun getPlanned(): Flow<List<Bookmark>> {
        return flow { emit(emptyList()) }
    }

    override fun getAlreadyWatched(): Flow<List<Bookmark>> {
        return flow { emit(emptyList()) }
    }

    override fun getDelayed(): Flow<List<Bookmark>> {
        return flow { emit(emptyList()) }
    }

    override fun getAbandoned(): Flow<List<Bookmark>> {
        return flow { emit(emptyList()) }
    }

    override suspend fun updateBookmark(bookmark: Bookmark) {

    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {

    }

    override fun getBookmarks(): Flow<List<Bookmark>> {
        return flow { emit(emptyList()) }
    }

    override suspend fun getBookmarkById(animeId: Int): Bookmark? {
        when (animeId) {
            1 -> {
                throw CancellationException()
            }
            2 -> {
                throw NullPointerException()
            }
            3 -> {
                return Bookmark(
                    12,
                    BookmarkTypes.WATCHED.name,
                    true,
                    animeId = 3
                )
            }
            else -> {
                return null
            }
        }
    }

}