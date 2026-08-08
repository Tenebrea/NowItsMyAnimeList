package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository

class GetBookmarkUseCase(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(id: Int): Bookmark? {
        return repository.getBookmarkById(id)
    }
}