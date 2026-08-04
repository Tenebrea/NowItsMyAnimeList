package com.example.nowitsmyanimelist.di

import androidx.room.Room
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.BookmarksDb
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource.repositories.BookmarkRepositoryImpl
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.networkModule
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.repositories.AnimeRepositoryImpl
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.AnimeRepository
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories.BookmarkRepository
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.AnimeUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.BookmarkUseCases
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetAnnouncedAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetBookmarkUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetFinishedAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetOngoingAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.useCases.GetTrendingAnimeUseCase
import com.example.nowitsmyanimelist.featureAnimeBrowsing.presentation.anime.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {
    includes(networkModule)

    single<AnimeRepository> {
        AnimeRepositoryImpl(get())
    }

    single {
        Room.databaseBuilder(
            get(),
            BookmarksDb::class.java,
            BookmarksDb.DATABASE_NAME
        ).build()
    }

    single {
        get<BookmarksDb>().bookmarkDao()
    }

    single<BookmarkRepository> {
        BookmarkRepositoryImpl(get())
    }

    single {
        GetAnimeUseCase(get())
    }
    single {
        GetAnnouncedAnimeUseCase(get())
    }
    single {
        GetBookmarkUseCase(get())
    }
    single {
        GetFinishedAnimeUseCase(get())
    }
    single {
        GetOngoingAnimeUseCase(get())
    }
    single {
        GetTrendingAnimeUseCase(get())
    }
    single {
        AnimeUseCases(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single {
        BookmarkUseCases(
            get()
        )
    }
    viewModel<HomeViewModel> {
        HomeViewModel(get(), get())
    }
}