package com.example.pokmonpictorialbook.data.database.entitiy

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_genera",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        )
    ],
    indices = [Index("pokemonId")]
)
data class PokemonGeneraEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,
    val languageCode: String,
    val genera: String
)