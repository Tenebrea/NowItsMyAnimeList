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
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class BookmarksDb : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var instance: BookmarksDb? = null
        fun getInstance(context: Context): BookmarksDb {
            if (instance==null) {
                instance = Room.databaseBuilder(context, BookmarksDb::class.java,"bookmarks.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return  instance as BookmarksDb
        }
    }
}