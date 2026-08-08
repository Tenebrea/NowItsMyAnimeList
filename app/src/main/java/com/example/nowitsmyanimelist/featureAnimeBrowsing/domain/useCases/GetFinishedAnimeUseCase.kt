package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.AnimeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class GetFinishedAnimeUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(startingPage: Int) =
        repository.getFinishedAnime(startingPage)
}