package com.example.nowitsmyanimelist.utils.mocks

import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.AnimeRepository
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.io.IOException

class AnimeRepositoryMock : AnimeRepository {
    override suspend fun getOngoingAnime(startingPage: Int): List<Anime> {
        throw HttpRequestTimeoutException(
            url = "",
            timeoutMillis = 20,
            cause = UnresolvedAddressException()
        )
    }

    override suspend fun getAnnouncedAnime(startingPage: Int): List<Anime> {
        throw IOException()
    }

    override suspend fun getTrendingAnime(startingPage: Int): List<Anime> {
        throw UnresolvedAddressException()
    }

    override suspend fun getFinishedAnime(startingPage: Int): List<Anime> {
        val animeList = List(40) {
            Anime(
                id = 1,
                title = "Naruto",
                description = "Some description",
                episodes = 12,
                isAdult = false,
                trending = 14,
                genres = listOf("Action", "Adventure"),
                meanScore = 6,
                status = MediaStatus.FINISHED.name,
                studios = listOf("Mappa"),
                coverImage = ""
            )
        }

        return animeList
    }
}