package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils

sealed class OnScreenDetailShown {
    object None: OnScreenDetailShown()
    object AnimeBottomSheet: OnScreenDetailShown()
    data class SelectBookmarkDialog(val error: Throwable? = null): OnScreenDetailShown()
}