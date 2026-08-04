package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.AnimeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class GetTrendingAnimeUseCase(
    private val repository: AnimeRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(startingPage: Flow<Int>, allowAdult: Boolean) =
        repository.getTrendingAnime(startingPage)
            .mapLatest { anime -> anime.filter{ if (!allowAdult) !it.isAdult else true } }
}