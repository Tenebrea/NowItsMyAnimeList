package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getAnime(id: Int): Anime
    fun getOngoingAnime(startingPage: Flow<Int>): Flow<List<Anime>>
    fun getAnnouncedAnime(startingPage: Flow<Int>): Flow<List<Anime>>
    fun getTrendingAnime(startingPage: Flow<Int>): Flow<List<Anime>>
    fun getFinishedAnime(startingPage: Flow<Int>): Flow<List<Anime>>
}