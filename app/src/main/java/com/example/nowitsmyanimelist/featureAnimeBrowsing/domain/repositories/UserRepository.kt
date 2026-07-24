package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.repositories

import coil3.BitmapImage
import com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models.User

interface UserRepository {
    val currentUser: User?

    fun getStatistics(): Map<String, Float>
    fun getUser(): User
    fun changeProfileIcon(newImage: BitmapImage)
    fun changeStatus(newString: String)
    fun changeUser(newUser: User)
}