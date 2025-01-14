package com.example.pokmonpictorialbook.data.repository

import android.graphics.Bitmap
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntities
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonSpeciesEntity

interface PokemonRepository {

    // Api
    suspend fun fetchTotalNumberOfPokemonFromApi(limit: Int = 10000, offset: Int = 0): List<PokemonEntity>
    suspend fun fetchPokemonFromApi(pokemonName: String): PokemonDetailEntity
    suspend fun fetchPokemonSpeciesFromApi(pokemonName: String): PokemonSpeciesEntity
    // Database
    // PokemonEntity
    suspend fun insertAllPokemon(pokemonEntityList: List<PokemonEntity>)
    suspend fun fetchAllPokemonFromDatabase(): List<PokemonEntity?>
    // PokemonDetailEntity
    suspend fun insertPokemonDetailEntities(pokemonDetailEntities: PokemonDetailEntities)
    suspend fun fetchAllPokemonDetailFromDatabase(): List<PokemonDetailEntity?>
    suspend fun fetchPokemonDetailFromDatabase(pokemonId: Int): PokemonDetailEntity?
    suspend fun fetchPokemonNameWithTranslationFromDatabase(pokemonId: Int, languageCode: String): PokemonNameEntity?
    suspend fun fetchPokemonGeneraWithTranslationFromDatabase(pokemonId: Int, languageCode: String): PokemonGeneraEntity?
    suspend fun fetchPokemonFlavorTextWithTranslationFromDatabase(pokemonId: Int, languageCode: String): PokemonFlavorTextEntity?
    // Storage
    suspend fun downloadImage(imageName: String, url: String)
    suspend fun fetchImage(imageName: String): Bitmap?
}