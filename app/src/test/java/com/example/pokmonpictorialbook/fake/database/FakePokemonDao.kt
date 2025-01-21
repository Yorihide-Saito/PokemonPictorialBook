package com.example.pokmonpictorialbook.fake.database

import com.example.pokmonpictorialbook.data.database.dao.PokemonDao
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity

class FakePokemonDao : PokemonDao {

    private val pokemonEntityList: MutableList<PokemonEntity> = mutableListOf()
    private val pokemonDetailEntityList: MutableList<PokemonDetailEntity> = mutableListOf()
    private val pokemonNameEntityList: MutableList<PokemonNameEntity> = mutableListOf()
    private val pokemonFlavorTextEntityList: MutableList<PokemonFlavorTextEntity> = mutableListOf()
    private val pokemonGeneraEntityList: MutableList<PokemonGeneraEntity> = mutableListOf()

    override suspend fun insertPokemonList(pokemonEntityList: List<PokemonEntity>) {
        this.pokemonEntityList.addAll(pokemonEntityList)
    }

    override suspend fun insertPokemonDetail(pokemonDetailEntity: PokemonDetailEntity) {
        this.pokemonDetailEntityList.add(pokemonDetailEntity)
    }

    override suspend fun insertPokemonName(pokemonNameEntity: PokemonNameEntity) {
        this.pokemonNameEntityList.add(pokemonNameEntity)
    }

    override suspend fun insertPokemonFlavorText(pokemonFlavorText: PokemonFlavorTextEntity) {
        this.pokemonFlavorTextEntityList.add(pokemonFlavorText)
    }

    override suspend fun insertPokemonGenera(pokemonGeneraEntity: PokemonGeneraEntity) {
        this.pokemonGeneraEntityList.add(pokemonGeneraEntity)
    }

    override suspend fun fetchPokemonById(id: Int): PokemonEntity? {
        return this.pokemonEntityList.find {
            it.id == id
        }
    }

    override suspend fun fetchAllPokemon(): List<PokemonEntity> {
        return this.pokemonEntityList
    }

    override suspend fun fetchAllPokemonDetail(): List<PokemonDetailEntity> {
        return this.pokemonDetailEntityList
    }

    override suspend fun fetchPokemonDetailById(pokemonId: Int): PokemonDetailEntity? {
        return this.pokemonDetailEntityList.find {
            it.pokemonId == pokemonId
        }
    }

    override suspend fun fetchPokemonNameWithTranslation(
        speciesId: Int,
        languageCode: String
    ): PokemonNameEntity? {
        return this.pokemonNameEntityList.find {
            it.id == speciesId && it.languageCode == languageCode
        }
    }

    override suspend fun fetchPokemonGeneraWithTranslation(
        speciesId: Int,
        languageCode: String
    ): PokemonGeneraEntity? {
        return this.pokemonGeneraEntityList.find {
            it.id == speciesId && it.languageCode == languageCode
        }
    }

    override suspend fun fetchPokemonFlavorTextWithTranslation(
        speciesId: Int,
        languageCode: String
    ): PokemonFlavorTextEntity? {
        return this.pokemonFlavorTextEntityList.find {
            it.id == speciesId && it.languageCode == languageCode
        }
    }
}