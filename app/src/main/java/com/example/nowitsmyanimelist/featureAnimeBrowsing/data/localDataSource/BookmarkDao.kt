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
    @Query("SELECT * FROM bookmark WHERE bookmark = :type")
    fun getBookmarksByType(type: String): Flow<List<Bookmark>>

    @Query("SELECT * FROM bookmark WHERE id = :id")
    fun getBookmarkById(id: Int): Bookmark?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)
}