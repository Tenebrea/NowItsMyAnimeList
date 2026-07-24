package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark

@Database(entities = [Bookmark::class], version = 1)
abstract class BookmarksDb : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}