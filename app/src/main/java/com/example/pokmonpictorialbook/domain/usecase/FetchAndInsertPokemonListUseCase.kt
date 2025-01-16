package com.example.pokmonpictorialbook.domain.usecase

import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonListFromDataBaseUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonListUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.InsertPokemonListUseCase
import com.example.pokmonpictorialbook.ui.feature.model.PokemonListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchAndInsertPokemonListUseCase(
    private val fetchPokemonListUseCase: FetchPokemonListUseCase,
    private val insertPokemonListUseCase: InsertPokemonListUseCase,
    private val fetchPokemonListFromDataBaseUseCase: FetchPokemonListFromDataBaseUseCase
) {
    operator fun invoke(): Flow<PokemonListData> {
        return flow {
            val pokemonEntityList: List<PokemonEntity> = fetchPokemonListUseCase().first()
            insertPokemonListUseCase(pokemonEntityList = pokemonEntityList)
            emit(fetchPokemonListFromDataBaseUseCase().first())
        }.flowOn(Dispatchers.IO).catch {
            throw it
        }
    }
}