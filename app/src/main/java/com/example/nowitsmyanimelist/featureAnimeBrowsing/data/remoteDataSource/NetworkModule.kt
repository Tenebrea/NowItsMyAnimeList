package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson

object NetworkModule {
    val client by lazy {
        Log.d("NetworkModule", "Initializing HttpClient")
        HttpClient(CIO) {
            install(ContentNegotiation) {
                gson()
            }
        }
    }
}
