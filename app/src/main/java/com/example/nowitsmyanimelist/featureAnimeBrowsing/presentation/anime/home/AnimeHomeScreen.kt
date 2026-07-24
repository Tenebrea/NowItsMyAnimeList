package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.AnimeList
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.AnimeTabs
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab

@Composable
fun AnimeHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    globalNavController: NavHostController
) {
    val navController = rememberNavController()
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(modifier = modifier) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnimeTabs(
                modifier = Modifier.fillMaxWidth(),
                tabs = HomeTab.entries,
                selectedIndex = uiState.value.currentPage.ordinal,
                onTabSelected = { viewModel.onEvent(HomeEvent.ChangePage(HomeTab.entries[it])) }
            )
            NavHost(
                navController = navController,
                startDestination = HomeTab.ONGOING.name,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = HomeTab.ONGOING.name) {
                    AnimeList(
                        animeBookmarkPairs = mapOf(),
                        onShowMoreOptions = {  }
                    )
                }
            }
        }
    }
}