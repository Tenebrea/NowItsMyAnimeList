package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import android.provider.MediaStore
import androidx.paging.PagingData
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

data class HomeUiState(
    val loadingState: RequestState = RequestState.Loading,
    val currentTab: HomeTab = HomeTab.ONGOING,
    val searchString: String = "",
    val allowAdult: Boolean = false,
    val bottomSheetShown: Boolean = false,
    // Одного флага видимости недостаточно: sheet должен знать, какой элемент изменяют его действия.
    val selectedPair: AnimeBookmarkPair? = null,
    val animeLists: Map<HomeTab, Flow<PagingData<AnimeBookmarkPair>>> = emptyMap(),
    val bookmarkDialogShown: Boolean = false,
    val dialogBookmark: Bookmark? = null
)
