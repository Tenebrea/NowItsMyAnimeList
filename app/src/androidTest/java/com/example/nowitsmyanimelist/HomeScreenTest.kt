package com.example.nowitsmyanimelist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home.AnimeHomeScreen
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home.HomeUiState
import com.example.nowitsmyanimelist.ui.theme.NowItsMyAnimeListTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            NowItsMyAnimeListTheme {
                AnimeHomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    uiState = HomeUiState(),
                    onEvent = {  }
                )
            }
        }
    }

    @Test
    fun homeScreen_onCreation_notCrashedOnEmptyList() {
        composeTestRule.onNodeWithTag("LoadingScreen").assertIsDisplayed()
    }
}