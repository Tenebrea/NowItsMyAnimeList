package com.example.nowitsmyanimelist

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.sqlite.execSQL
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.BookmarksDb
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.MIGRATION_1_2
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val TEST_DB = "test-migration"
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    @get:Rule
    val helper = MigrationTestHelper(
        instrumentation = instrumentation,
        databaseClass = BookmarksDb::class,
        driver = AndroidSQLiteDriver(),
        file = instrumentation.targetContext.getDatabasePath(TEST_DB)
    )

    @Test
    fun migrate1To2() = runTest {
        val connection = helper.createDatabase(1)
        connection.execSQL("""
            INSERT INTO bookmark (bookmark, is_favorite, anime_id) 
            VALUES
            ('WATCHING', 1, 123),
            (NULL, 1, 123),
            ('WATCHED', 0, 13),
            (NULL, 1, 10);
        """.trimIndent()
        )
        connection.close()

        val migratedConnection = helper
            .runMigrationsAndValidate(2, listOf(MIGRATION_1_2))

        val hasData = migratedConnection.prepare("SELECT COUNT(*) FROM bookmark").use {
            it.step()
            it.getLong(0) == 3L
        }
        assertTrue("Expected data was not migrated", hasData)
        migratedConnection.close()
    }
}