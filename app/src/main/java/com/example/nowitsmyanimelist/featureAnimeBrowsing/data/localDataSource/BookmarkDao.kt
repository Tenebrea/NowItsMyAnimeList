package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    // Избранное - независимый флаг, поэтому его нельзя определить по статусу закладки.
    @Query("SELECT * FROM bookmark WHERE is_favorite = 1")
    fun getFavorites(): Flow<List<Bookmark>>

    @Query("SELECT * FROM bookmark WHERE bookmark = :type")
    fun getBookmarksByType(type: String): Flow<List<Bookmark>>

    // Экран работает с идентификатором аниме из AniList, а не с первичным ключом строки закладки.
    @Query("SELECT * FROM bookmark WHERE anime_id = :animeId")
    suspend fun getBookmarkByAnimeId(animeId: Int): Bookmark?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)
}
