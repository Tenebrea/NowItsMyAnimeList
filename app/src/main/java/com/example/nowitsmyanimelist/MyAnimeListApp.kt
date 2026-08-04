package com.example.nowitsmyanimelist

import android.app.Application
import com.example.nowitsmyanimelist.di.mediaModule
import com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyAnimeListApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyAnimeListApp)
            modules(
                networkModule, mediaModule
            )
        }
    }
}