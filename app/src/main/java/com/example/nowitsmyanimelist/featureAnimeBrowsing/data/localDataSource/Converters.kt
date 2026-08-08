package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun convertListToJSONString(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    fun convertJSONStringToList(string: String): String = Json.decodeFromString(string)
}