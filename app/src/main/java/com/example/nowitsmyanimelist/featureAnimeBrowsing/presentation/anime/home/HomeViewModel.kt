package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nowitsmyanimelist.PAGE_JUMP
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.AnimeUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val repository: AnimeUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    private val ongoingPage = MutableStateFlow(1)
    private val announcedPage = MutableStateFlow(1)
    private val finishedPage = MutableStateFlow(1)
    private val trendingPage = MutableStateFlow(1)
    private var job: Job? = null

    init {
        getAnimeByTab(_uiState.value.currentTab)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetAnime -> {
                updatePages(_uiState.value.currentTab)
            }
            is HomeEvent.ChangeTab -> {
                _uiState.update { it.copy(currentTab = event.tab) }
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
                viewModelScope.launch {
                    repository.getOngoingAnime(ongoingPage, _uiState.value.allowAdult).collect { animeList ->
                        val newList = _uiState.value.displayedAnimeLists as MutableMap
                        newList[HomeTab.ONGOING] = animeList
                        _uiState.update { state ->
                            state.copy(
                                displayedAnimeLists = newList
                            )
                        }
                    }
                }
            }
            HomeTab.ANNOUNCED -> {
                viewModelScope.launch {
                    repository.getAnnouncedAnime(announcedPage, _uiState.value.allowAdult).collect { animeList ->
                        val newList = _uiState.value.displayedAnimeLists as MutableMap
                        newList[HomeTab.ANNOUNCED] = animeList
                        _uiState.update { state ->
                            state.copy(
                                displayedAnimeLists = newList
                            )
                        }
                    }
                }
            }
            HomeTab.FINISHED -> {
                viewModelScope.launch {
                    repository.getFinishedAnime(finishedPage, _uiState.value.allowAdult).collect { animeList ->
                        val newList = _uiState.value.displayedAnimeLists as MutableMap
                        newList[HomeTab.FINISHED] = animeList
                        _uiState.update { state ->
                            state.copy(
                                displayedAnimeLists = newList
                            )
                        }
                    }
                }
            }
            HomeTab.TRENDING -> {
                viewModelScope.launch {
                    repository.getTrendingAnime(trendingPage, _uiState.value.allowAdult).collect { animeList ->
                        val newList = _uiState.value.displayedAnimeLists as MutableMap
                        newList[HomeTab.TRENDING] = animeList
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
}