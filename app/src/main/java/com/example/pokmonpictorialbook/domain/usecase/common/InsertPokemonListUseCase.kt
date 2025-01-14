package com.example.pokmonpictorialbook.domain.usecase.common

import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class InsertPokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(pokemonEntityList: List<PokemonEntity>) {
        // Database に値を格納する。
        runBlocking(Dispatchers.IO) {
            repository.insertAllPokemon(pokemonEntityList)
        }
    }
}