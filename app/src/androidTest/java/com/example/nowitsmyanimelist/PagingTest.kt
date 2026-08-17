package com.example.nowitsmyanimelist

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.testing.asSnapshot
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.paging.AnimePagingSource
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.AnimeUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetAnnouncedAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetFinishedAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetOngoingAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetTrendingAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import com.example.nowitsmyanimelist.utils.mocks.AnimeRepositoryMock
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PagingTest {
    val animeRepository = AnimeRepositoryMock()
    val animeUseCases: AnimeUseCases = AnimeUseCases(
        getAnime = GetAnimeUseCase(animeRepository),
        getAnnouncedAnime = GetAnnouncedAnimeUseCase(animeRepository),
        getOngoingAnime = GetOngoingAnimeUseCase(animeRepository),
        getTrendingAnime = GetTrendingAnimeUseCase(animeRepository),
        getFinishedAnime = GetFinishedAnimeUseCase(animeRepository)
    )

    @Test
    fun paging_checkCorrectMessageOnHttpRequestTimeoutException() = runTest {
        val pagingSource = AnimePagingSource(animeUseCases, HomeTab.ONGOING)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )
        assertTrue(result is PagingSource.LoadResult.Error)
        if (result is PagingSource.LoadResult.Error)
        assertEquals("Timeout. Please try again later", result.throwable.message, )
    }

    @Test
    fun paging_checkCorrectMessageOnIOException() = runTest {
        val pagingSource = AnimePagingSource(animeUseCases, HomeTab.ANNOUNCED)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )
        assertTrue(result is PagingSource.LoadResult.Error)
        if (result is PagingSource.LoadResult.Error)
        assertEquals("IOException", result.throwable.message)
    }

    @Test
    fun paging_checkCorrectMessageOnUnresolvedAddressException() = runTest {
        val pagingSource = AnimePagingSource(animeUseCases, HomeTab.TRENDING)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )
        assertTrue(result is PagingSource.LoadResult.Error)
        if (result is PagingSource.LoadResult.Error)
        assertEquals("Connection error happened", result.throwable.message)
    }

    @Test
    fun paging_checkSuccessfulLoad() = runTest {
        val pagingSource = AnimePagingSource(animeUseCases, HomeTab.FINISHED)
        val animeList = List(40) {
            Anime(
                id = 1,
                title = "Naruto",
                description = "Some description",
                episodes = 12,
                isAdult = false,
                trending = 14,
                genres = listOf("Action", "Adventure"),
                meanScore = 6,
                status = MediaStatus.FINISHED.name,
                studios = listOf("Mappa"),
                coverImage = ""
            )
        }

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )
        assertTrue(result is PagingSource.LoadResult.Page)
        if (result is PagingSource.LoadResult.Page)
        assertEquals(animeList, result.data)
    }
}