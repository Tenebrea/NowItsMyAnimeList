package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.AnimeList
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.AnimeTabs
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import com.example.nowitsmyanimelist.ui.theme.NowItsMyAnimeListTheme

@Composable
fun AnimeHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    val navController = rememberNavController()
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(modifier = modifier) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnimeTabs(
                modifier = Modifier.fillMaxWidth(),
                tabs = HomeTab.entries,
                selectedIndex = uiState.value.currentTab.ordinal,
                onTabSelected = {
                    viewModel.onEvent(HomeEvent.ChangeTab(HomeTab.entries[it]))
                    navController.navigate("HomeTab?tabType=${HomeTab.entries[it].name}")
                }
            )
            NavHost(
                navController = navController,
                startDestination = "HomeTab?tabType=${HomeTab.ONGOING.name}",
                modifier = Modifier.fillMaxSize()
            ) {
                composable(
                    route = "HomeTab?tabType={tabType}",
                    arguments = listOf(
                        navArgument("tabType") {
                            type = NavType.StringType
                            defaultValue = HomeTab.ONGOING.name
                        }
                    )
                ) { args ->
                    val tab = HomeTab.entries.find { it.name == args.arguments?.getString("tabType") }
                    AnimeList(
                        animeBookmarkPairs = uiState.value.displayedAnimeLists[tab] ?: emptyList(),
                        onShowMoreOptions = { anime ->
                            viewModel.onEvent(
                                HomeEvent.UpdateBottomSheet(
                                    anime
                                )
                            )
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    NowItsMyAnimeListTheme {
        AnimeHomeScreen(viewModel = viewModel())
    }
}