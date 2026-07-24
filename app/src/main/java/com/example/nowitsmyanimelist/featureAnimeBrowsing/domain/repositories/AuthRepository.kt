package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories

import com.example.nowitsmyanimelist.AuthResponse

interface AuthRepository {
    val accessToken: String
    val refreshToken: String

    fun login(userName: String, password: String): AuthResponse
    fun logout()
    fun newRefreshToken()
}

