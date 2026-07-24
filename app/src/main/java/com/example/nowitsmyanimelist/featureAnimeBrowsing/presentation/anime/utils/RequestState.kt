package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils

sealed class RequestState {
    object Success: RequestState()
    data class Error(val message: String): RequestState()
    object Loading: RequestState()
}