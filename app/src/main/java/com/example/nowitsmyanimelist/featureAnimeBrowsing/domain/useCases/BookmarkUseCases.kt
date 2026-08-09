package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

// Одна зависимость предоставляет экрану все необходимые операции с закладками.
data class BookmarkUseCases(
    val getBookmark: GetBookmarkUseCase,
    val updateBookmark: UpdateBookmarkUseCase,
    val deleteBookmark: DeleteBookmarkUseCase
)
