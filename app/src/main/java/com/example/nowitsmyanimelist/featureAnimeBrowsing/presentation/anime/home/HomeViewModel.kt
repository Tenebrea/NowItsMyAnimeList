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
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.AnimeUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.BookmarkUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.LoadingState
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.OnScreenDetailShown
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val animeRepository: AnimeUseCases,
    private val bookmarkRepository: BookmarkUseCases,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    private var loadingState: Job? = null

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
                _uiState.update {
                    it.copy(
                        onScreenDetailShown = OnScreenDetailShown.AnimeBottomSheet,
                        selectedPair = event.pair
                    )
                }
            }

            HomeEvent.DismissBottomSheet -> {
                loadingState?.cancel()
                _uiState.update {
                    it.copy(
                        onScreenDetailShown = OnScreenDetailShown.None,
                        selectedPair = null
                    )
                }
            }

            is HomeEvent.ToggleFavorite -> {
                loadingState?.cancel()
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
                loadingState = viewModelScope.launch(dispatcher) {
                    try {
                        _uiState.update {
                            it.copy(
                                dialogBookmark = bookmarkRepository.getBookmarkById(event.anime.id),
                                onScreenDetailShown = OnScreenDetailShown.SelectBookmarkDialog()
                            )
                        }
                    } catch (e: CancellationException) {
                        _uiState.update {
                            it.copy(
                                onScreenDetailShown = OnScreenDetailShown.None
                            )
                        }
                    } catch (e: Exception) {
                        _uiState.update {
                            it.copy(
                                onScreenDetailShown = OnScreenDetailShown.SelectBookmarkDialog(e)
                            )
                        }
                    }
                }
            }

            is HomeEvent.ChangeBookmark -> {
                // Без выбранного аниме невозможно выполнить корректное изменение базы данных.
                val selectedPair = _uiState.value.selectedPair ?: return
                loadingState = viewModelScope.launch(dispatcher) {
                    val current = _uiState.value.dialogBookmark
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
                            onScreenDetailShown = OnScreenDetailShown.None,
                            dialogBookmark = null
                        )
                    }
                }
            }

            is HomeEvent.DismissDialog -> {
                _uiState.update {
                    it.copy(
                        onScreenDetailShown = OnScreenDetailShown.None,
                        selectedPair = null
                    )
                }
            }
        }
    }

    private fun changeTab(homeTab: HomeTab) {
        if (!_uiState.value.animeLists.containsKey(homeTab)) {
            loadData(homeTab)
        } else {
            _uiState.update {
                it.copy(
                    currentTab = homeTab
                )
            }
        }
    }

    private fun loadData(homeTab: HomeTab) = viewModelScope.launch {
        val newList = _uiState.value.animeLists.toMutableMap()
        newList[homeTab] = LoadingState.Loading
        _uiState.update {
            it.copy(
                animeLists = newList,
                currentTab = homeTab
            )
        }
        newList[homeTab] = LoadingState.Done(pager(homeTab))
        _uiState.update {
            it.copy(
                animeLists = newList
            )
        }
    }

    private fun pager(homeTab: HomeTab): Flow<PagingData<AnimeBookmarkPair>> {
        val bookmarkFlow = bookmarkRepository
            .getBookmarks()
            .map { it.associateBy { bookmark -> bookmark.animeId } }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_JUMP,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { AnimePagingSource(animeRepository, homeTab) }
        )
            .flow
            .cachedIn(viewModelScope)
            .combine(bookmarkFlow) { pagingData, bookmarks ->
                pagingData.map { anime ->
                    AnimeBookmarkPair(
                        anime = anime,
                        bookmark = bookmarks[anime.id]
                    )
                }
            }
    }
}
