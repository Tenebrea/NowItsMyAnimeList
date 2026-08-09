package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository

// Удаление оформлено как доменное действие, а не прямой вызов DAO из presentation-слоя.
class DeleteBookmarkUseCase(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark) = repository.deleteBookmark(bookmark)
}
