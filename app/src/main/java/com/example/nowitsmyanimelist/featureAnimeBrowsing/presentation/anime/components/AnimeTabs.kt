package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun <T> AnimeTabs(
    modifier: Modifier = Modifier,
    tabs: List<T>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    SecondaryScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        scrollState = scrollState,
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = index == selectedIndex,
                onClick = {
                    onTabSelected(index)
                },
                text = {
                    Text(text = tab.toString())
                }
            )
        }
    }
}

@Preview
@Composable
fun AnimeTabsPreview() {
    AnimeTabs(
        tabs = listOf("Ongoing", "Announced", "Finished", "Trending"),
        selectedIndex = 0,
        onTabSelected = {}
    )
}