package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.ui.theme.NowItsMyAnimeListTheme

@Composable
fun BookmarkDialog(
    onBookmarkChange: (BookmarkTypes?) -> Unit,
    bookmark: Bookmark?,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = true
        )
    ) {
        DialogContent(
            onBookmarkChange = onBookmarkChange,
            bookmark = bookmark
        )
    }
}

@Composable
fun DialogContent(
    onBookmarkChange: (BookmarkTypes?) -> Unit,
    bookmark: Bookmark?
) {
    val radioOptions: List<BookmarkTypes?> = BookmarkTypes.entries + null
    Card(
        modifier = Modifier
            .height(300.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(
                text = "Choose bookmark status"
            )
            Column(
                modifier = Modifier
                    .selectableGroup()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                radioOptions.forEach { type ->
                    Row(
                        modifier = Modifier
                            .clickable(onClick = { onBookmarkChange(type) }),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (type == null) {
                            RadioButton(
                                selected = (bookmark == null) || (bookmark.bookmark == null),
                                onClick = null
                            )
                            Text(
                                text = "NOT IN THE LIST"
                            )
                        } else {
                            RadioButton(
                                selected = (bookmark?.bookmark == type.name),
                                onClick = null
                            )
                            Text(
                                text = type.name
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkDialogPreview() {
    NowItsMyAnimeListTheme() {
        DialogContent(
            onBookmarkChange = { bookmark -> },
            bookmark = null,
        )
    }
}