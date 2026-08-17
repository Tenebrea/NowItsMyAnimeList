package com.example.nowitsmyanimelist.featureAnimeBrowsing.domain.models

data class GraphQlException(
    override val message: String
) : Exception()
