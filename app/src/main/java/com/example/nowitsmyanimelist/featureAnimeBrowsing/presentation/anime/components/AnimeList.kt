package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.MediaCoverImage
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Studio
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.StudioConnection
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Titles

@Composable
fun AnimeList(
    animeBookmarkPairs: Map<Anime, Bookmark?>,
    onShowMoreOptions: (Anime) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(animeBookmarkPairs.keys.toList()) { anime ->
            AnimeCard(
                anime = anime,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                bookmark = animeBookmarkPairs[anime],
                onShowMoreOptions = { onShowMoreOptions(anime) },
                isFavorite = animeBookmarkPairs[anime]?.isFavorite ?: false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeListPreview() {
    AnimeList(
        animeBookmarkPairs = mapOf(
            Anime(
                id = 1,
                title = Titles(
                    romaji = "Some Anime"
                ),
                description = "Some Anime Description",
                episodes = 22,
                isAdult = false,
                trending = 11,
                genres = listOf("Action", "Adventure"),
                meanScore = 5,
                status = MediaStatus.RELEASING.toString(),
                studios = StudioConnection(nodes = listOf(Studio("Mappa"))),
                coverImage = MediaCoverImage(null)
            ) to Bookmark(
                id = 1,
                bookmark = "Watching",
                isFavorite = true
            ),
            Anime(
                id = 2,
                title = Titles(
                    romaji = "Other Anime"
                ),
                description = "Other Anime Description",
                episodes = 11,
                isAdult = false,
                trending = 12,
                genres = listOf("Action", "Adventure"),
                meanScore = 3,
                status = MediaStatus.FINISHED.toString(),
                studios = StudioConnection(nodes = listOf(Studio("MadHouse"))),
                coverImage = MediaCoverImage(null)
            ) to Bookmark(
                id = 1,
                bookmark = "Watched",
                isFavorite = false
            ),
        ),
        onShowMoreOptions = {  }
    )
}