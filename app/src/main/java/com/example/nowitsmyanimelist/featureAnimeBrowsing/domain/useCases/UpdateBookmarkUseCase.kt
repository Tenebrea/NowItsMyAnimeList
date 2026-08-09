package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository

// Сохраняет независимость ViewModel от реализации репозитория и API Room.
class UpdateBookmarkUseCase(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark) = repository.updateBookmark(bookmark)
}
