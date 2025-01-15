package com.example.pokmonpictorialbook.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonEntityList: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonDetail(pokemonDetailEntity: PokemonDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonName(pokemonNameEntity: PokemonNameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonFlavorText(pokemonFlavorText: PokemonFlavorTextEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonGenera(pokemonGeneraEntity: PokemonGeneraEntity)

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM pokemon WHERE id = :id LIMIT 1")
    suspend fun fetchPokemonById(id: Int): PokemonEntity?

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM pokemon")
    suspend fun fetchAllPokemon(): List<PokemonEntity>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM pokemon_detail")
    suspend fun fetchAllPokemonDetail(): List<PokemonDetailEntity>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT pokemon_detail.pokemonId, pokemon_detail.height, pokemon_detail.weight, pokemon_detail.types, pokemon_detail.backDefault, pokemon_detail.frontDefault
        FROM pokemon
        INNER JOIN pokemon_detail
        ON pokemon.id = pokemon_detail.pokemonId
        WHERE pokemon.id = :pokemonId
    """)
    suspend fun fetchPokemonDetailById(pokemonId: Int): PokemonDetailEntity?

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT pokemon_names.id, pokemon_names.pokemonId, pokemon_names.languageCode, pokemon_names.name 
        FROM pokemon
        INNER JOIN pokemon_names
        ON pokemon.id = pokemon_names.pokemonId
        WHERE pokemon.id = :speciesId AND pokemon_names.languageCode = :languageCode
    """)
    suspend fun fetchPokemonNameWithTranslation(speciesId: Int, languageCode: String): PokemonNameEntity?

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT pokemon_genera.id, pokemon_genera.pokemonId, pokemon_genera.languageCode, pokemon_genera.genera 
        FROM pokemon
        INNER JOIN pokemon_genera
        ON pokemon.id = pokemon_genera.pokemonId
        WHERE pokemon.id = :speciesId AND pokemon_genera.languageCode = :languageCode
    """)
    suspend fun fetchPokemonGeneraWithTranslation(speciesId: Int, languageCode: String): PokemonGeneraEntity?

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT pokemon_flavor_texts.id, pokemon_flavor_texts.pokemonId, pokemon_flavor_texts.languageCode, pokemon_flavor_texts.flavorText 
        FROM pokemon
        INNER JOIN pokemon_flavor_texts
        ON pokemon.id = pokemon_flavor_texts.pokemonId
        WHERE pokemon.id = :speciesId AND pokemon_flavor_texts.languageCode = :languageCode
    """)
    suspend fun fetchPokemonFlavorTextWithTranslation(speciesId: Int, languageCode: String): PokemonFlavorTextEntity?
}