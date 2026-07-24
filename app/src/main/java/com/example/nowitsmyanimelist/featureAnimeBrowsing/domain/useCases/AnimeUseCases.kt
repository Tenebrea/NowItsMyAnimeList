package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases

data class AnimeUseCases(
    val getAnime: GetAnimeUseCase,
    val getAnnouncedAnime: GetAnnouncedAnimeUseCase,
    val getOngoingAnime: GetOngoingAnimeUseCase,
    val getTrendingAnime: GetTrendingAnime,
    val getFinishedAnime: GetFinishedAnimeUseCase
)
