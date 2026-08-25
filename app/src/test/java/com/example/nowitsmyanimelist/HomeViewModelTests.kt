package com.example.nowitsmyanimelist

import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.AnimeUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.BookmarkUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.DeleteBookmarkUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetAnnouncedAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetBookmarkByIdUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetBookmarksUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetFinishedAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetOngoingAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetTrendingAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.UpdateBookmarkUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home.HomeEvent
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home.HomeViewModel
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.OnScreenDetailShown
import com.example.nowitsmyanimelist.utils.AnimeRepositoryMock
import com.example.nowitsmyanimelist.utils.BookmarkRepositoryMock
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HomeViewModelTests {
    val animeRepository = AnimeRepositoryMock()
    val bookmarkRepository = BookmarkRepositoryMock()

    val animeUseCases: AnimeUseCases = AnimeUseCases(
        getAnnouncedAnime = GetAnnouncedAnimeUseCase(animeRepository),
        getOngoingAnime = GetOngoingAnimeUseCase(animeRepository),
        getTrendingAnime = GetTrendingAnimeUseCase(animeRepository),
        getFinishedAnime = GetFinishedAnimeUseCase(animeRepository)
    )
    val bookmarkUseCases: BookmarkUseCases = BookmarkUseCases(
        getBookmarks = GetBookmarksUseCase(bookmarkRepository),
        updateBookmark = UpdateBookmarkUseCase(bookmarkRepository),
        deleteBookmark = DeleteBookmarkUseCase(bookmarkRepository),
        getBookmarkById = GetBookmarkByIdUseCase(bookmarkRepository)
    )

    private lateinit var viewModel: HomeViewModel
    private lateinit var testDispatcher: TestDispatcher
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(
            animeRepository = animeUseCases,
            bookmarkRepository = bookmarkUseCases,
            dispatcher = Dispatchers.Main
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_OpenDialog_CancellationErrorInCoroutine() = runTest {
        viewModel.onEvent(
            HomeEvent.OpenDialog(
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
            )
        )
        advanceUntilIdle()

        val uiState = viewModel.uiState.value

        assertEquals(OnScreenDetailShown.None, uiState.onScreenDetailShown)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_OpenDialog_ErrorInCoroutine() = runTest {
        viewModel.onEvent(
            HomeEvent.OpenDialog(
                Anime(
                    id = 2,
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
            )
        )
        advanceUntilIdle()

        val uiState = viewModel.uiState.value

        assertTrue("Current uiState show is ${uiState.onScreenDetailShown}", uiState.onScreenDetailShown is OnScreenDetailShown.SelectBookmarkDialog)
        if (uiState.onScreenDetailShown is OnScreenDetailShown.SelectBookmarkDialog) {
            assertTrue("current error is ${uiState.onScreenDetailShown.error}", uiState.onScreenDetailShown.error is NullPointerException)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun homeViewModel_OpenDialog_Success() = runTest {
        viewModel.onEvent(
            HomeEvent.OpenDialog(
                Anime(
                    id = 3,
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
            )
        )
        advanceUntilIdle()
        val uiState = viewModel.uiState.value

        assertTrue("Current uiState show is ${uiState.onScreenDetailShown}", uiState.onScreenDetailShown is OnScreenDetailShown.SelectBookmarkDialog)
        if (uiState.onScreenDetailShown is OnScreenDetailShown.SelectBookmarkDialog) {
            assertTrue("current error is ${uiState.onScreenDetailShown.error}", uiState.onScreenDetailShown.error == null)
        }
    }
}