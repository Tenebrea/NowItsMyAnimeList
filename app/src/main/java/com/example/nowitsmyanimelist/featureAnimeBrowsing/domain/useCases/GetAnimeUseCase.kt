package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

import com.example.nowitsmyanimelist.BookmarkTypes
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.AnimeRepository

class GetAnimeUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(bookmarkTypes: BookmarkTypes): List<Anime> {
        val result = mutableListOf<Anime>()


        return result
    }
}