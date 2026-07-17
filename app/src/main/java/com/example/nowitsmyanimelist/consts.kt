package com.example.nowitsmyanimelist

import com.example.nowitsmyanimelist.models.User

enum class BookmarkTypes {
    WATCHING, PLANNED, WATCHED, DELAYED, ABANDONED
}

sealed class AuthResponse {
    data class Success(val user: User) : AuthResponse()
    data object Error : AuthResponse()
}