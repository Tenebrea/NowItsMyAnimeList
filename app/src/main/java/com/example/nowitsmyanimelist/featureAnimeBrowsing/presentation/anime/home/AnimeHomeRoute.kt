package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun AnimeHomeRoute(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimeHomeScreen(
        modifier = modifier,
        onEvent = { event -> viewModel.onEvent(event) },
        uiState = uiState
    )
}