package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.repositories

import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.AnimeApi
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
    override suspend fun getAnime(id: Int): Anime {
        return api.getAnimeById(id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getOngoingAnime(startingPage: Flow<Int>): Flow<List<Anime>> = startingPage
        .distinctUntilChanged()
        .mapLatest { api.getAnime(it, MediaStatus.RELEASING, SortType.POPULARITY_DESC) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAnnouncedAnime(startingPage: Flow<Int>): Flow<List<Anime>> = startingPage
        .distinctUntilChanged()
        .mapLatest { api.getAnime(it, MediaStatus.NOT_YET_RELEASED, SortType.POPULARITY_DESC) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFinishedAnime(startingPage: Flow<Int>): Flow<List<Anime>> = startingPage
        .distinctUntilChanged()
        .mapLatest { api.getAnime(it, MediaStatus.FINISHED, SortType.POPULARITY_DESC) }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTrendingAnime(startingPage: Flow<Int>): Flow<List<Anime>> = startingPage
        .distinctUntilChanged()
        .mapLatest { startingPage ->
            api.getAnime(startingPage, MediaStatus.RELEASING, SortType.TRENDING_DESC)
        }
}