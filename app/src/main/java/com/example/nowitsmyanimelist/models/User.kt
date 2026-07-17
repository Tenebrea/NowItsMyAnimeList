package com.example.nowitsmyanimelist.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    val userName: String?,
    val password: String,
    val email: String?,
    val profilePicture: String?,
    val status: String = ""
)
