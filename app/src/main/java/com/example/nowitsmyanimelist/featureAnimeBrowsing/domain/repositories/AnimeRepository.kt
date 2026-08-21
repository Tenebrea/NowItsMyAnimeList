package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getOngoingAnime(startingPage: Int): List<Anime>
    suspend fun getAnnouncedAnime(startingPage: Int): List<Anime>
    suspend fun getTrendingAnime(startingPage: Int): List<Anime>
    suspend fun getFinishedAnime(startingPage: Int): List<Anime>
}