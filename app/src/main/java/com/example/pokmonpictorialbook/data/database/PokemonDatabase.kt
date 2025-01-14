package com.example.pokmonpictorialbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokmonpictorialbook.data.database.dao.PokemonDao
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonDetailEntity::class,
        PokemonGeneraEntity::class,
        PokemonNameEntity::class,
        PokemonFlavorTextEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}