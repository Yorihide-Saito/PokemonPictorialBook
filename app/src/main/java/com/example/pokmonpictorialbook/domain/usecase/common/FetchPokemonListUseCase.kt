package com.example.pokmonpictorialbook.domain.usecase.common

import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchPokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<List<PokemonEntity>> {
        return flow {
            // 最新情報に更新
            val pokemonListResponseList: List<PokemonEntity> =
                repository.fetchTotalNumberOfPokemonFromApi()
            emit(pokemonListResponseList)
        }.flowOn(Dispatchers.IO).catch {
            throw it
        }
    }
}