package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed class LoadingState {
    object Loading: LoadingState()
    data class Done(val pager: Flow<PagingData<AnimeBookmarkPair>>): LoadingState()
}