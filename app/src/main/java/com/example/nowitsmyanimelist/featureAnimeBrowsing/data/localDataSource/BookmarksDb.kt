package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark

@Database(
    entities = [Bookmark::class, Anime::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(value = [Converters::class])
abstract class BookmarksDb : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        // Общее имя не позволяет DI и старой фабрике случайно открыть разные базы данных.
        const val DATABASE_NAME = "bookmarks.db"
    }
}
