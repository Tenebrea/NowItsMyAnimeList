package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Bookmark
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarksUseCase(
    private val repository: BookmarkRepository
) {
    operator fun invoke(): Flow<List<Bookmark>> {
        return repository.getBookmarks()
    }
}