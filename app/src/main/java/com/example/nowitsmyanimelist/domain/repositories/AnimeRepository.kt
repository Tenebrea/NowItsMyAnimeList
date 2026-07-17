package com.example.nowitsmyanimelist.domain.repositories

import com.example.nowitsmyanimelist.models.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getAnime(id: Long): Anime
    fun getOngoingAnime(): Flow<List<Anime>>
    fun getAnnouncedAnime(): Flow<List<Anime>>
    fun getTrendingAnime(): Flow<List<Anime>>
}