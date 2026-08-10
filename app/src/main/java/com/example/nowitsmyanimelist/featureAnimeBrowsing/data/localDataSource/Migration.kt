package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            ALTER TABLE bookmark
            RENAME TO bookmark_old
            """.trimIndent()
        )

        connection.execSQL("""
            DROP INDEX IF EXISTS index_bookmark_anime_id;
        """.trimIndent())

        connection.execSQL(
            """
            CREATE TABLE bookmark (
                id INTEGER NOT NULL PRIMARY KEY,
                bookmark TEXT,
                is_favorite INTEGER NOT NULL,
                anime_id INTEGER NOT NULL
            )
            """.trimIndent()
        )

        connection.execSQL(
            """
            INSERT INTO bookmark (
                bookmark,
                is_favorite,
                anime_id
            )
            SELECT
                bookmark,
                is_favorite,
                anime_id
            FROM bookmark_old
            GROUP BY anime_id
            """.trimIndent()
        )

        connection.execSQL(
            """
            CREATE UNIQUE INDEX index_bookmark_anime_id
            ON bookmark(anime_id)
            """.trimIndent()
        )

        connection.execSQL(
            "DROP TABLE bookmark_old"
        )
    }
}