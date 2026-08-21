package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.repositories

import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.AnimeApi
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.toAnime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.SortType
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.AnimeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest



class AnimeRepositoryImpl(
    private val api: AnimeApi
) : AnimeRepository {

    override suspend fun getOngoingAnime(startingPage: Int): List<Anime> = api
        .getAnime(startingPage, MediaStatus.RELEASING, SortType.POPULARITY_DESC)
        .map { it.toAnime() }

    override suspend fun getAnnouncedAnime(startingPage: Int): List<Anime> = api
        .getAnime(startingPage, MediaStatus.NOT_YET_RELEASED, SortType.POPULARITY_DESC)
        .map { it.toAnime() }

    override suspend fun getFinishedAnime(startingPage: Int): List<Anime> = api
        .getAnime(startingPage, MediaStatus.FINISHED, SortType.POPULARITY_DESC)
        .map { it.toAnime() }

    override suspend fun getTrendingAnime(startingPage: Int): List<Anime> = api
        .getAnime(startingPage, MediaStatus.RELEASING, SortType.TRENDING_DESC)
        .map { it.toAnime() }
}