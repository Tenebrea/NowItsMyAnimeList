package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair
import kotlinx.coroutines.flow.Flow

@Composable
fun AnimeList(
    animeBookmarkPairs: Flow<PagingData<AnimeBookmarkPair>>,
    onShowMoreOptions: (AnimeBookmarkPair) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyPagingItems = animeBookmarkPairs.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier) {
        items(
            lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.anime.id }
        ) { index ->
            val pair = lazyPagingItems[index]
            if (pair != null) {
                AnimeCard(
                    anime = pair.anime,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    bookmark = pair.bookmark,
                    onShowMoreOptions = { onShowMoreOptions(pair) },
                    isFavorite = pair.bookmark?.isFavorite ?: false
                )
            }
        }
    }
}