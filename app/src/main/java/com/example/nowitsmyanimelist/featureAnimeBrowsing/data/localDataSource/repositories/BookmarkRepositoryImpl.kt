package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.repositories

import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.BookmarkDao
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    // Детали хранения данных должны оставаться внутри реализации репозитория.
    private val dao: BookmarkDao
) : BookmarkRepository {
    override fun getFavorites(): Flow<List<Bookmark>> {
        return dao.getFavorites()
    }

    override fun getWatching(): Flow<List<Bookmark>> {
        return dao.getBookmarksByType(BookmarkTypes.WATCHING.name)
    }

    override fun getPlanned(): Flow<List<Bookmark>> {
        return dao.getBookmarksByType(BookmarkTypes.PLANNED.name)
    }

    override fun getAlreadyWatched(): Flow<List<Bookmark>> {
        return dao.getBookmarksByType(BookmarkTypes.WATCHED.name)
    }

    override fun getDelayed(): Flow<List<Bookmark>> {
        return dao.getBookmarksByType(BookmarkTypes.DELAYED.name)
    }

    override fun getAbandoned(): Flow<List<Bookmark>> {
        return dao.getBookmarksByType(BookmarkTypes.ABANDONED.name)
    }

    // REPLACE позволяет одной операцией и создавать, и изменять закладку.
    override suspend fun updateBookmark(
        bookmark: Bookmark
    ) {
        dao.insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        dao.deleteBookmark(bookmark)
    }

    // Вызывающий код передаёт ID аниме — именно этот внешний идентификатор используется в UI.
    override suspend fun getBookmarkById(id: Int): Bookmark? {
        return dao.getBookmarkByAnimeId(id)
    }
}
