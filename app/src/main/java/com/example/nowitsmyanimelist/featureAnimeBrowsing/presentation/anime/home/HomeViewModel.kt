package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nowitsmyanimelist.PAGE_JUMP
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.paging.AnimePagingSource
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.AnimeUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.BookmarkUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val animeRepository: AnimeUseCases,
    val bookmarkRepository: BookmarkUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        changeTab(_uiState.value.currentTab)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ChangeTab -> {
                changeTab(event.tab)
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

    private fun changeTab(homeTab: HomeTab) {
        val newList = _uiState.value.animeLists.toMutableMap()

        if (!_uiState.value.animeLists.containsKey(homeTab)) {
            newList[homeTab] = pager(homeTab)
        }

        _uiState.update {
            it.copy(
                currentTab = homeTab,
                animeLists = newList
            )
        }
    }

    private fun pager(homeTab: HomeTab) = Pager(
            config = PagingConfig(
                pageSize = PAGE_JUMP,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { AnimePagingSource(animeRepository, homeTab) }
        )
            .flow
            .map { source -> source.map { AnimeBookmarkPair(it, bookmarkRepository.getBookmark(it.id)) } }
            .cachedIn(viewModelScope)
}