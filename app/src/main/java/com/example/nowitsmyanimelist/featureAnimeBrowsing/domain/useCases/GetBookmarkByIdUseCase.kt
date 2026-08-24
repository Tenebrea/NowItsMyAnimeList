package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository

class GetBookmarkByIdUseCase(val repository: BookmarkRepository) {
    suspend operator fun invoke(animeId: Int): Bookmark? {
        return repository.getBookmarkById(animeId)
    }
}