package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab

sealed interface HomeEvent {
    object GetAnime: HomeEvent
    data class ChangeTab(val tab: HomeTab): HomeEvent
    data class UpdateBottomSheet(val anime: Anime): HomeEvent
    data class OpenDialog(val anime: Anime): HomeEvent
    object DismissDialog: HomeEvent
}