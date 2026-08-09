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
                // Сохраняем выбранный элемент вместе с флагом, чтобы действия sheet имели безопасную цель.
                _uiState.update { it.copy(bottomSheetShown = true, selectedPair = event.pair) }
            }
            HomeEvent.DismissBottomSheet -> {
                _uiState.update { it.copy(bottomSheetShown = false, selectedPair = null) }
            }
            is HomeEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    // Избранное может существовать без статуса списка, поэтому при необходимости создаём строку.
                    val current = event.pair.bookmark
                    val updated = current?.copy(isFavorite = !current.isFavorite)
                        ?: com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark(
                            id = event.pair.anime.id.toLong(),
                            bookmark = null,
                            isFavorite = true,
                            animeId = event.pair.anime.id
                        )
                    bookmarkRepository.updateBookmark(updated)
                    // Обновляем содержимое sheet сразу, не ожидая обновления Paging.
                    _uiState.update {
                        it.copy(selectedPair = event.pair.copy(bookmark = updated))
                    }
                }
            }

            is HomeEvent.OpenDialog -> {
                // Модальные sheet и dialog не должны оставаться видимыми друг поверх друга.
                _uiState.update { it.copy(bottomSheetShown = false) }

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

            is HomeEvent.ChangeBookmark -> {
                // Без выбранного аниме невозможно выполнить корректное изменение базы данных.
                val selectedPair = _uiState.value.selectedPair ?: return
                viewModelScope.launch {
                    val current = bookmarkRepository.getBookmark(selectedPair.anime.id)
                    val updated = current?.copy(bookmark = event.type?.name)
                        ?: event.type?.let {
                            com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark(
                                id = selectedPair.anime.id.toLong(),
                                bookmark = it.name,
                                isFavorite = false,
                                animeId = selectedPair.anime.id
                            )
                        }

                    // Удаляем пустую строку, но сохраняем её, если аниме всё ещё отмечено как избранное.
                    when {
                        updated == null -> Unit
                        updated.bookmark == null && !updated.isFavorite ->
                            bookmarkRepository.deleteBookmark(updated)
                        else -> bookmarkRepository.updateBookmark(updated)
                    }
                    _uiState.update {
                        it.copy(
                            bookmarkDialogShown = false,
                            selectedPair = null
                        )
                    }
                }
            }

            is HomeEvent.DismissDialog -> {
                _uiState.update {
                    it.copy(
                        bookmarkDialogShown = false,
                        selectedPair = null
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
