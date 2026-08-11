package com.example.nowitsmyanimelist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nowitsmyanimelist.di.mediaModule
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components.AnimeList
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home.AnimeHomeScreen
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home.HomeUiState
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home.HomeViewModel
import com.example.nowitsmyanimelist.ui.theme.NowItsMyAnimeListTheme
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.collections.listOf

class HomeScreenTest : KoinTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            NowItsMyAnimeListTheme {
                AnimeHomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    uiState = HomeUiState(),
                    onEvent = { event -> }
                )
            }
        }
    }

    @Test
    fun homeScreen_onCreation_notCrashedOnEmptyList() {
        composeTestRule.onNodeWithTag("LoadingScreen").assertIsDisplayed()
    }
}