package com.example.pokmonpictorialbook.data.database.entitiy

import com.example.pokmonpictorialbook.features.ui.model.PokemonDetail

// TODO これどっかに移動したい。合わせて、PokemonSpeciesEntityも
data class PokemonDetailEntities(
    val pokemonDetailEntity: PokemonDetailEntity,
    val pokemonSpeciesEntity: PokemonSpeciesEntity
)
