package com.example.nowitsmyanimelist.featureAnimeBrowsing.data.localDataSource

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun convertListToJSONString(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    // Room нужен точный обратный тип для List<String> -> String, чтобы восстановить поля сущности.
    fun convertJSONStringToList(string: String): List<String> = Json.decodeFromString(string)
}
