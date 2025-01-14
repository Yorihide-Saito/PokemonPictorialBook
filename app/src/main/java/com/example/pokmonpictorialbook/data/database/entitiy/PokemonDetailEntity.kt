package com.example.pokmonpictorialbook.data.database.entitiy

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(
    tableName = "pokemon_detail",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        )
    ],
    indices = [Index("pokemonId")]
)
@TypeConverters(Converters::class)
data class PokemonDetailEntity(
    @PrimaryKey val pokemonId: Int,
    val height: Int,
    val weight: Int,
    val types: List<String?>,
    val backDefault: String,
    val frontDefault: String
)

class Converters {
    @TypeConverter
    fun fromStringList(value: String?): List<String?>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String?>?): String? {
        return Gson().toJson(list)
    }
}