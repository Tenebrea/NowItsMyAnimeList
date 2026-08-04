package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeBottomSheet(
    pair: AnimeBookmarkPair,
    onDismiss: () -> Unit,
    onFavorite: (AnimeBookmarkPair) -> Unit,
    onAddBookmark: (AnimeBookmarkPair) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AnimeDetails(
                anime = pair.anime,
                bookmark = pair.bookmark,
                modifier = Modifier
            )
            ButtonWithIcon(
                modifier = Modifier.fillMaxWidth(),
                icon = if (pair.bookmark?.isFavorite ?: false) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                tint = if (pair.bookmark?.isFavorite ?: false) MaterialTheme.colorScheme.secondary else Color(0xFFBA9531),
                text = if (pair.bookmark?.isFavorite ?: false) "Remove Favorite" else "Add to Favorite",
                onClick = { onFavorite(pair) }
            )
            ButtonWithIcon(
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.AutoMirrored.Filled.List,
                tint = MaterialTheme.colorScheme.secondary,
                text = "Bookmark: ${pair.bookmark?.bookmark ?: "Not watching"}",
                onClick = { onAddBookmark(pair) }
            )
        }
    }
}

@Composable
fun AnimeDetails(
    anime: Anime,
    bookmark: Bookmark?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = anime.title.romaji ?: "Unknown",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (anime.status == MediaStatus.NOT_YET_RELEASED.name) {
                Text(
                    text = "? ep",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                Text(
                    text = if (anime.status != MediaStatus.FINISHED.name) "${anime.episodes} out of ? ep" else "${anime.episodes} ep",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground

                )
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = anime.meanScore.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null
                    )
                }
            }
            if (bookmark?.isFavorite ?: false) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    tint = Color(0xFFBA9531),
                    contentDescription = "Favorite Anime"
                )
            }
        }
    }
}

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    tint: Color,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .semantics(properties = {
                contentDescription = text
            })
    ) {
        Icon(
            imageVector = icon,
            tint = tint,
            contentDescription = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}