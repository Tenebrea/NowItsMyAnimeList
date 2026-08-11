package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair
import kotlinx.coroutines.flow.Flow

@Composable
fun AnimeList(
    animeBookmarkPairs: Flow<PagingData<AnimeBookmarkPair>>,
    onShowMoreOptions: (AnimeBookmarkPair) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyPagingItems = animeBookmarkPairs.collectAsLazyPagingItems()
    val scrollState = rememberLazyListState()
    when (val refreshState = lazyPagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is LoadState.Error -> {
            RetryScreen(
                modifier = modifier,
                onRefresh = { lazyPagingItems.refresh() },
                message = refreshState.error.message ?: "Unexpected error has occurred"
            )
        }

        is LoadState.NotLoading if lazyPagingItems.itemCount == 0 -> {
            RetryScreen (
                modifier = Modifier.fillMaxSize(),
                message = "Nothing was found",
                onRefresh = { lazyPagingItems.refresh() }
            )
        }

        else -> {
            LazyColumn(
                modifier = modifier,
                state = scrollState
            ) {
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
                item{
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (lazyPagingItems.loadState.refresh is LoadState.NotLoading) {
                            Button(onClick = { lazyPagingItems.refresh() }) {
                                Text(
                                    text = "Retry"
                                )
                            }
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.width(16.dp),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}