package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark

@Composable
fun BookmarkDialog(
    modifier: Modifier = Modifier,
    onBookmarkChange: (BookmarkTypes?) -> Unit,
    bookmark: Bookmark?
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Choose bookmark status"
        )

        RadioButton(
            selected = (bookmark == null) || (bookmark.bookmark == null),
            onClick = { onBookmarkChange(null) },
            modifier = Modifier
        )

        BookmarkTypes.entries.forEach { type ->
            RadioButton(
                selected = (bookmark?.bookmark == type.name),
                onClick = { onBookmarkChange(type) },
            )
        }
    }
}