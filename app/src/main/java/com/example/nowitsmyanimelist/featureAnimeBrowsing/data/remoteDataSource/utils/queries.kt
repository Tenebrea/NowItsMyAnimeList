package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.remoteDataSource.utils

import com.example.nowitsmyanimelist.PAGE_JUMP

internal val animeListByStatusQuery = """
query {
  Page(page: %d, perPage: $PAGE_JUMP) {
    media (type: ANIME, status: %s, sort: %s){
      id
      title {
        romaji
      }
      description
      episodes
      trending
      genres
      isAdult
      meanScore
      studios(isMain: true) {
        nodes {
          name
        }
      }
      coverImage {
        medium
      }
    }
  }
}
""".trimIndent()

internal val animeById = """
    query {
      Media(type: ANIME, id: %d) {
        id
          title {
            romaji
          }
          description
          episodes
          trending
          genres
          meanScore
          studios(isMain: true) {
            nodes {
              name
            }
          }
          coverImage {
            medium
          }
          isAdult
      }
    }
""".trimIndent()