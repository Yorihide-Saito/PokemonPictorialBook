package com.example.pokmonpictorialbook.data.database.entitiy

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_names",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        )
    ],
    indices = [Index("pokemonId")]
)
data class PokemonNameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,
    val languageCode: String,
    val name: String
)