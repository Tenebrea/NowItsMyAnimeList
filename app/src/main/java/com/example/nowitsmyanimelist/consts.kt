package com.example.nowitsmyanimelist

import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.User

enum class BookmarkTypes {
    WATCHING, PLANNED, WATCHED, DELAYED, ABANDONED
}

sealed class AuthResponse {
    data class Success(val user: User) : AuthResponse()
    data object Error : AuthResponse()
}


const val  PAGE_JUMP = 10