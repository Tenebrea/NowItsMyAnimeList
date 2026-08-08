package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.repositories

import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.BookmarkDao
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    val dao: BookmarkDao
) : BookmarkRepository {
    override fun getFavorites(): Flow<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override fun getWatching(): Flow<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override fun getPlanned(): Flow<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override fun getAlreadyWatched(): Flow<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override fun getDelayed(): Flow<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override fun getAbandoned(): Flow<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override fun updateBookmark(
        bookmark: Bookmark
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteBookmark(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }

    override suspend fun getBookmarkById(id: Int): Bookmark? {
        return dao.getBookmarkById(id)
    }
}