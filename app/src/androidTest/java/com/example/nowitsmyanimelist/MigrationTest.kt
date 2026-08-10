package com.example.nowitsmyanimelist

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.sqlite.execSQL
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.BookmarksDb
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.MIGRATION_1_2
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
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
            (NULL, 0, 123),
            ('WATCHED', 0, 13),
            (NULL, 1, 10);
        """.trimIndent()
        )
        connection.close()

        val migratedConnection = helper
            .runMigrationsAndValidate(2, listOf(MIGRATION_1_2))

        val statement = migratedConnection.prepare("SELECT * FROM bookmark")

        statement.use {
            assertTrue(it.step())
            assertTrue("First anime has wrong anime_id, which is ${it.getInt(3)}", it.getInt(3) == 123)
            assertTrue("First anime has wrong id, which is ${it.getLong(0)}", it.getLong(0) == 1L)
            assertTrue("First anime has a bookmark", it.isNull(1))
            assertTrue("First anime is favorite, which is wrong", !it.getBoolean(2))

            assertTrue(it.step())
            assertTrue("Second anime has wrong anime_id, which is ${it.getInt(3)}", it.getInt(3) == 13)
            assertTrue("Second anime has wrong id, which is ${it.getLong(0)}", it.getLong(0) == 2L)
            assertFalse("Second anime has no bookmark", it.isNull(1))
            assertTrue("Second anime has wrong bookmark", it.getText(1) == "WATCHED")
            assertTrue("Second anime is favorite, which is wrong", !it.getBoolean(2))

            assertTrue(it.step())
            assertTrue("Third anime has wrong anime_id, which is ${it.getInt(3)}", it.getInt(3) == 10)
            assertTrue("Third anime has wrong id, which is ${it.getLong(0)}", it.getLong(0) == 3L)
            assertTrue("Third anime has a bookmark", it.isNull(1))
            assertTrue("Third anime is not favorite", it.getBoolean(2))

            assertFalse(it.step())
        }
        migratedConnection.close()
    }
}