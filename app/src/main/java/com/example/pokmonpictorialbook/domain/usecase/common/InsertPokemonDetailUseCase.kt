package com.example.pokmonpictorialbook.domain.usecase.common

import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntities
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class InsertPokemonDetailUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(pokemonDetailEntities: PokemonDetailEntities) {
        // DataBase に値を格納する。
        runBlocking(Dispatchers.IO) {
            pokemonRepository.insertPokemonDetailEntities(pokemonDetailEntities)
        }
    }
}