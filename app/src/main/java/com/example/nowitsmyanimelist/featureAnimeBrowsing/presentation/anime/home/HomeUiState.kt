package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import android.provider.MediaStore
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.RequestState

data class HomeUiState(
    val loadingState: RequestState = RequestState.Loading,
    val displayedAnimeLists: Map<HomeTab, List<Anime>> = mapOf(
        HomeTab.ONGOING to emptyList(),
        HomeTab.ANNOUNCED to emptyList(),
        HomeTab.FINISHED to emptyList(),
        HomeTab.TRENDING to emptyList()
    ),
    val currentTab: HomeTab = HomeTab.ONGOING,
    val searchString: String = "",
    val allowAdult: Boolean = false
)
