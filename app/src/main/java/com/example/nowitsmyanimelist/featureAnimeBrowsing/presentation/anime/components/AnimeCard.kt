package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.ModifierLocalModifierNode
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.R
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
@Composable
fun AnimeCard(
    modifier: Modifier = Modifier,
    anime: Anime,
    bookmark: Bookmark?,
    onShowMoreOptions: () -> Unit,
    isFavorite: Boolean
) {
    Row(
        modifier = modifier,
    ) {
        AnimeImage(
            model = anime.coverImage,
            contentDescription = anime.title,
            bookmark = bookmark,
            modifier = Modifier
                .width(96.dp)
                .height(140.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(end = 4.dp)
        )
        AnimeDescription(
            modifier = Modifier.fillMaxWidth(),
            anime = anime,
            isFavorite = isFavorite,
            onShowMoreOptions = { onShowMoreOptions() }
        )
    }
}

@Composable
fun AnimeImage(
    modifier: Modifier = Modifier,
    model: Any?,
    contentDescription: String?,
    bookmark: Bookmark?
) {
    Box(modifier = modifier) {
        if (model != "") {
            AsyncImage(
                modifier = Modifier
                    .matchParentSize(),
                model = model,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(
                modifier = Modifier.matchParentSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.missing_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        if (bookmark?.bookmark != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(45.dp))
                    .background(
                        when (bookmark.bookmark) {
                            BookmarkTypes.WATCHING.name -> Color(0xFF218F3F)
                            BookmarkTypes.WATCHED.name -> Color(0xFF71A3F6)
                            BookmarkTypes.ABANDONED.name -> Color(0xFFDF3D30)
                            BookmarkTypes.DELAYED.name -> Color(0xFFC29E4A)
                            BookmarkTypes.PLANNED.name -> Color(0xFF9466CC)
                            else -> Color.Transparent
                        }
                    )
            ) {
                Text(
                    text = bookmark.bookmark,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AnimeDescription(
    modifier: Modifier = Modifier,
    anime: Anime,
    isFavorite: Boolean,
    onShowMoreOptions: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = anime.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = onShowMoreOptions,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.show_more_options)
                )
            }
        }

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
                    text = "${anime.episodes} ep",
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
            if (isFavorite) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    tint = Color(0xFFBA9531),
                    contentDescription = "Favorite Anime"
                )
            }
        }
        Text(
            text = anime.description ?: "",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeCardPreview() {
    AnimeCard(
        anime = Anime(
            id = 1,
            title = "Naruto but I reincarnated as a slime in another world with disbalanced ability and my party betrayed me",
            description = "Some description",
            episodes = 12,
            isAdult = false,
            trending = 14,
            genres = listOf("Action", "Adventure"),
            meanScore = 6,
            status = MediaStatus.NOT_YET_RELEASED.name,
            studios = listOf("Mappa"),
            coverImage = ""
        ),
        // Закладка в preview должна ссылаться на то же аниме, чтобы корректно моделировать реальные данные.
        bookmark = Bookmark(12, BookmarkTypes.WATCHED.name, true, animeId = 1),
        isFavorite = true,
        onShowMoreOptions = {}
    )
}
