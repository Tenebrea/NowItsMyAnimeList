package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

data class AnimeUseCases(
    val getAnnouncedAnime: GetAnnouncedAnimeUseCase,
    val getOngoingAnime: GetOngoingAnimeUseCase,
    val getTrendingAnime: GetTrendingAnimeUseCase,
    val getFinishedAnime: GetFinishedAnimeUseCase
)
