package com.example.pokmonpictorialbook.domain.usecase.common

import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntities
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonSpeciesEntity
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale

class FetchPokemonDetailUseCase(
    private val pokemonRepository: PokemonRepository
) {
    val languageCode: String = Locale.getDefault().language

    operator fun invoke(pokemonName: String): Flow<PokemonDetailEntities> {
        return flow {
            val pokemonDetailEntity: PokemonDetailEntity = pokemonRepository.fetchPokemonFromApi(pokemonName)
            val pokemonSpeciesEntity: PokemonSpeciesEntity = pokemonRepository.fetchPokemonSpeciesFromApi(pokemonName)
            emit(
                PokemonDetailEntities(
                    pokemonDetailEntity = pokemonDetailEntity,
                    pokemonSpeciesEntity = pokemonSpeciesEntity
                )
            )
        }.flowOn(Dispatchers.IO).catch {
            throw it
        }
    }

}