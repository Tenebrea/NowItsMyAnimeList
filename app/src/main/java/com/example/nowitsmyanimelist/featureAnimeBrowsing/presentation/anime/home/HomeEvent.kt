package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home

import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.AnimeBookmarkPair
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab

sealed interface HomeEvent {
    data class ChangeTab(val tab: HomeTab): HomeEvent
    data class UpdateBottomSheet(val pair: AnimeBookmarkPair): HomeEvent
    // Явные события закрытия и действий делают смену состояния предсказуемой вместо инверсии Boolean.
    data object DismissBottomSheet: HomeEvent
    data class ToggleFavorite(val pair: AnimeBookmarkPair): HomeEvent
    data class OpenDialog(val anime: Anime): HomeEvent
    // null означает, что пользователь выбрал вариант «не добавлено в список».
    data class ChangeBookmark(val type: BookmarkTypes?): HomeEvent
    object DismissDialog: HomeEvent
}
