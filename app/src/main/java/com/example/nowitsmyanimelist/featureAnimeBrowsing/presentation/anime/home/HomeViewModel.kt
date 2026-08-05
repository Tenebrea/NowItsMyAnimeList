package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nowitsmyanimelist.PAGE_JUMP
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.AnimeUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.BookmarkUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetBookmarkUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val animeRepository: AnimeUseCases,
    val bookmarkRepository: BookmarkUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    private val ongoingPage = MutableStateFlow(1)
    private val announcedPage = MutableStateFlow(1)
    private val finishedPage = MutableStateFlow(1)
    private val trendingPage = MutableStateFlow(1)
    private var job: Job? = null

    init {
        getAnimeByTab(HomeTab.ONGOING)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetAnime -> {
                updatePages(_uiState.value.currentTab)
            }

            is HomeEvent.ChangeTab -> {
                _uiState.update { it.copy(currentTab = event.tab) }
                getAnimeByTab(_uiState.value.currentTab)
            }

            is HomeEvent.UpdateBottomSheet -> {
                _uiState.update { it.copy(bottomSheetShown = !it.bottomSheetShown) }
            }
            is HomeEvent.OpenDialog -> {
                _uiState.update { it.copy(bottomSheetShown = !it.bottomSheetShown) }

                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            dialogBookmark = bookmarkRepository.getBookmark(event.anime.id)
                        )
                    }
                }.invokeOnCompletion {
                    _uiState.update {
                        it.copy(
                            bookmarkDialogShown = true
                        )
                    }
                }
            }
            is HomeEvent.DismissDialog -> {
                _uiState.update {
                    it.copy(
                        bookmarkDialogShown = false
                    )
                }
            }
        }
    }

    private fun updatePages(tab: HomeTab) {
        when (tab) {
            HomeTab.ONGOING -> ongoingPage.update { it + PAGE_JUMP }
            HomeTab.ANNOUNCED -> announcedPage.update { it + PAGE_JUMP }
            HomeTab.FINISHED -> finishedPage.update { it + PAGE_JUMP }
            HomeTab.TRENDING -> trendingPage.update { it + PAGE_JUMP }
        }
    }

    private fun getAnimeByTab(tab: HomeTab) {
        job?.cancel()
        job = when (tab) {
            HomeTab.ONGOING -> {
                viewModelScope.launch(Dispatchers.IO) {
                    animeRepository.getOngoingAnime(ongoingPage, _uiState.value.allowAdult)
                        .collect { animeList ->
                            val newList = _uiState.value.displayedAnimeLists.toMutableMap().apply {
                                this[HomeTab.ONGOING] = animeBookmarkMapper(animeList)
                            }
                            _uiState.update { state ->
                                state.copy(
                                    displayedAnimeLists = newList
                                )
                            }
                        }
                }
            }

            HomeTab.ANNOUNCED -> {
                viewModelScope.launch(Dispatchers.IO) {
                    animeRepository.getAnnouncedAnime(announcedPage, _uiState.value.allowAdult)
                        .collect { animeList ->
                            val newList = _uiState.value.displayedAnimeLists.toMutableMap().apply {
                                this[HomeTab.ANNOUNCED] = animeBookmarkMapper(animeList)
                            }
                            _uiState.update { state ->
                                state.copy(
                                    displayedAnimeLists = newList
                                )
                            }
                        }
                }
            }

            HomeTab.FINISHED -> {
                viewModelScope.launch(Dispatchers.IO) {
                    animeRepository.getFinishedAnime(finishedPage, _uiState.value.allowAdult)
                        .collect { animeList ->
                            val newList = _uiState.value.displayedAnimeLists.toMutableMap().apply {
                                this[HomeTab.FINISHED] = animeBookmarkMapper(animeList)
                            }
                            _uiState.update { state ->
                                state.copy(
                                    displayedAnimeLists = newList
                                )
                            }
                        }
                }
            }

            HomeTab.TRENDING -> {
                viewModelScope.launch(Dispatchers.IO) {
                    animeRepository.getTrendingAnime(trendingPage, _uiState.value.allowAdult)
                        .collect { animeList ->
                            val newList = _uiState.value.displayedAnimeLists.toMutableMap().apply {
                                this[HomeTab.TRENDING] = animeBookmarkMapper(animeList)
                            }
                            _uiState.update { state ->
                                state.copy(
                                    displayedAnimeLists = newList
                                )
                            }
                        }
                }
            }
        }
    }

    private fun animeBookmarkMapper(animeList: List<Anime>): List<AnimeBookmarkPair> {
        val animeBookmarkPairs = animeList
            .map { anime -> AnimeBookmarkPair(anime, bookmarkRepository.getBookmark(anime.id)) }

        return animeBookmarkPairs
    }
}