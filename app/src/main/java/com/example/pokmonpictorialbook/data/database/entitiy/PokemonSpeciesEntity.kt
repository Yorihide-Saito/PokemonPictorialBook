package com.example.pokmonpictorialbook.data.database.entitiy

data class PokemonSpeciesEntity(
    val pokemonNameEntity: List<PokemonNameEntity>,
    val pokemonFlavorTextEntity: List<PokemonFlavorTextEntity>,
    val pokemonGeneraEntity: List<PokemonGeneraEntity>
)