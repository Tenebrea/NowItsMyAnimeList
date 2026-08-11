package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.LoadingState

data class HomeUiState(
    val currentTab: HomeTab = HomeTab.ONGOING,
    val searchString: String = "",
    val allowAdult: Boolean = false,
    val bottomSheetShown: Boolean = false,
    // Одного флага видимости недостаточно: sheet должен знать, какой элемент изменяют его действия.
    val selectedPair: AnimeBookmarkPair? = null,
    val animeLists: Map<HomeTab, LoadingState> = emptyMap(),
    val bookmarkDialogShown: Boolean = false,
    val dialogBookmark: Bookmark? = null
)
