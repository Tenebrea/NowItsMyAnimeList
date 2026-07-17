package com.example.nowitsmyanimelist.models


data class Anime(
    val id: Long,
    val title: String,
    val description: String,
    val author: String,
    val studio: String,
    val genres: List<String>,
    val thumbnail: String,
    val rating: Float
)

