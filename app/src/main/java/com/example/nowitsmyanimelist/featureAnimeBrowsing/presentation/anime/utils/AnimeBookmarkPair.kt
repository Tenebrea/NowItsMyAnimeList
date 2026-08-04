package com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark

data class AnimeBookmarkPair(
    val anime: Anime,
    val bookmark: Bookmark?
)
