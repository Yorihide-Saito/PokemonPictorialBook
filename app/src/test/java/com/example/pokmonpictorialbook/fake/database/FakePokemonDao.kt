package com.example.pokmonpictorialbook.fake.database

import com.example.pokmonpictorialbook.data.database.dao.PokemonDao
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity

class FakePokemonDao : PokemonDao {

    override suspend fun insertPokemonList(pokemonEntityList: List<PokemonEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPokemonDetail(pokemonDetailEntity: PokemonDetailEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPokemonName(pokemonNameEntity: PokemonNameEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPokemonFlavorText(pokemonFlavorText: PokemonFlavorTextEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPokemonGenera(pokemonGeneraEntity: PokemonGeneraEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPokemonById(id: Int): PokemonEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllPokemon(): List<PokemonEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllPokemonDetail(): List<PokemonDetailEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPokemonDetailById(pokemonId: Int): PokemonDetailEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPokemonNameWithTranslation(
        speciesId: Int,
        languageCode: String
    ): PokemonNameEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPokemonGeneraWithTranslation(
        speciesId: Int,
        languageCode: String
    ): PokemonGeneraEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPokemonFlavorTextWithTranslation(
        speciesId: Int,
        languageCode: String
    ): PokemonFlavorTextEntity? {
        TODO("Not yet implemented")
    }
}