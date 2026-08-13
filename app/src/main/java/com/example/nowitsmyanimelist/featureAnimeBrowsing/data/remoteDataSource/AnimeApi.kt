package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource

import android.util.Log
import coil3.network.HttpException
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.BUILD_URL
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.MediaStatus
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.SortType
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.animeById
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils.animeListByStatusQuery
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.GraphQlException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AnimeApi(
    private val client: HttpClient
) {
    suspend fun getAnime(
        startingPage: Int,
        status: MediaStatus,
        sort: SortType
    ): List<AnimeDto> {
        return try {
            Log.d("AnimeApi", "Fetching page $startingPage with status $status")
            val query = animeListByStatusQuery
                .format(startingPage, status.name, sort.name)

            val response: GraphQlResponse<PageResponse> =
                client.post(BUILD_URL) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        GraphQlRequest(
                            query = query
                        )
                    )
                }.body()
            response.errors
                ?.takeIf { it.isNotEmpty() }
                ?.let { errors ->
                    throw GraphQlException(
                        errors.joinToString("\n") { it.message }
                    )
                }
            response.data?.Page?.media ?: emptyList()
        } catch (e: Exception) {
            Log.e("AnimeApi", "Error fetching anime", e)
            throw e
        }
    }

    suspend fun getAnimeById(id: Int): Anime {
        return try {
            Log.d("AnimeApi", "Fetching anime with id $id")
            val query = animeById.format(id)
            val response: GraphQlResponse<Anime> =
                client.post(BUILD_URL) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        GraphQlRequest(
                            query = query
                        )
                    )
                }.body()
            Log.d("AnimeApi", "Received ${response.data?.id ?: 0} items")
            response.data ?: throw Exception("No data received")
        } catch (e: Exception) {
            Log.e("AnimeApi", "Error fetching anime", e)
            throw e
        }
    }
}
