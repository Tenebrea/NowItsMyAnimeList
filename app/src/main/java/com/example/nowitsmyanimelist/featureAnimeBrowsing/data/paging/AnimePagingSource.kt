package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil3.network.HttpException
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.Anime
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.AnimeUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.utils.HomeTab
import kotlinx.io.IOException

class AnimePagingSource(
    val animeRepository: AnimeUseCases,
    val homeTab: HomeTab
) : PagingSource<Int, Anime>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        return try {
            // Paging уже передаёт следующую страницу в key; повторный инкремент пропускает страницы.
            val pageNumber = params.key ?: 1

            val response = when (homeTab) {
                HomeTab.TRENDING ->
                    animeRepository.getTrendingAnime(pageNumber)
                HomeTab.ONGOING ->
                    animeRepository.getOngoingAnime(pageNumber)
                HomeTab.ANNOUNCED ->
                    animeRepository.getAnnouncedAnime(pageNumber)
                HomeTab.FINISHED ->
                    animeRepository.getFinishedAnime(pageNumber)
            }

            LoadResult.Page(
                data = response,
                prevKey = if (pageNumber - 1 > 0) pageNumber - 1 else null,
                // null сообщает Paging о конце списка и предотвращает бесконечные пустые запросы.
                nextKey = if (response.isEmpty()) null else pageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
