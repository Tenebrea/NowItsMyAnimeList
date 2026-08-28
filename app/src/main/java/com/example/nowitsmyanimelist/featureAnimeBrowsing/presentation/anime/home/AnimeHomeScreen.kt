package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.AnimeBottomSheet
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.AnimeList
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.AnimeTabs
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.BookmarkDialog
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.LoadingScreen
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.LoadingState
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.OnScreenDetailShown
import com.example.nowitsmyanimelist.ui.theme.NowItsMyAnimeListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeHomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {
    val navController = rememberNavController()
    Scaffold(modifier = modifier) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnimeTabs(
                modifier = Modifier.fillMaxWidth(),
                tabs = HomeTab.entries,
                selectedIndex = uiState.currentTab.ordinal,
                onTabSelected = {
                    onEvent(HomeEvent.ChangeTab(HomeTab.entries[it]))
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
                    val tabString = args.arguments?.getString("tabType")
                    if (tabString != null) {
                        val tab = HomeTab.valueOf(tabString)
                        when (val state = uiState.animeLists[tab]) {
                            is LoadingState.Done -> {
                                AnimeList(
                                    animeBookmarkPairs = state.pager,
                                    onShowMoreOptions = { pair ->
                                        onEvent(
                                            HomeEvent.UpdateBottomSheet(
                                                pair
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                )
                            }
                            else -> {
                                LoadingScreen(modifier = Modifier.fillMaxSize())
                            }
                        }
                    }
                }
            }
            
            // Отображаем sheet только при наличии флага и данных, чтобы исключить падения из-за TODO()/null.
            val selectedPair = uiState.selectedPair
            when (uiState.onScreenDetailShown) {
                OnScreenDetailShown.None -> {

                }
                OnScreenDetailShown.AnimeBottomSheet -> {
                    if (selectedPair != null) {
                        AnimeBottomSheet(
                            pair = selectedPair,
                            onDismiss = { onEvent(HomeEvent.DismissBottomSheet) },
                            onFavorite = { onEvent(HomeEvent.ToggleFavorite(it)) },
                            onAddBookmark = { onEvent(HomeEvent.OpenDialog(it.anime)) }
                        )
                    }
                }
                is OnScreenDetailShown.SelectBookmarkDialog -> {
                    if (uiState.onScreenDetailShown.error == null) {
                        BookmarkDialog(
                            bookmark = uiState.dialogBookmark,
                            onBookmarkChange = { onEvent(HomeEvent.ChangeBookmark(it)) },
                            onDismissRequest = { onEvent(HomeEvent.DismissDialog) }
                        )
                    } else {
                        AlertDialog(
                            icon = {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = null
                                )
                            },
                            title = {
                                Text(text = "Error happened while opening a dialog")
                            },
                            text = {
                                Text(text = uiState.onScreenDetailShown.error.message ?: "Unexpected error happened")
                            },
                            onDismissRequest = {
                                onEvent(HomeEvent.DismissDialog)
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        onEvent(HomeEvent.DismissDialog)
                                    }
                                ) {
                                    Text("Ok")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
//советую использовать @PreviewLightAndDark
@Preview
@Composable
fun HomeScreenPreview() {
    NowItsMyAnimeListTheme {
        AnimeHomeScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = HomeUiState(),
            onEvent = {  }
        )
    }
}
