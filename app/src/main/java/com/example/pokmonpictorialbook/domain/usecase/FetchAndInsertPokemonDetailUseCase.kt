package com.example.pokmonpictorialbook.domain.usecase

import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntities
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonDetailFromDataBaseUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonDetailUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.InsertPokemonDetailUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.image.DownloadImageUseCase
import com.example.pokmonpictorialbook.ui.feature.model.PokemonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchAndInsertPokemonDetailUseCase(
    private val fetchPokemonDetailUseCase: FetchPokemonDetailUseCase,
    private val insertPokemonDetailUseCase: InsertPokemonDetailUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val fetchPokemonDetailFromDataBaseUseCase: FetchPokemonDetailFromDataBaseUseCase
) {
    operator fun invoke(pokemonName: String): Flow<PokemonDetail> {
        return flow {
            val pokemonDetailEntities: PokemonDetailEntities = fetchPokemonDetailUseCase(pokemonName).first()
            insertPokemonDetailUseCase(pokemonDetailEntities = pokemonDetailEntities)
            downloadImageUseCase(pokemonDetailEntities.pokemonDetailEntity)
            emit(fetchPokemonDetailFromDataBaseUseCase(pokemonDetailEntities.pokemonDetailEntity.pokemonId).first())
        }.flowOn(Dispatchers.IO).catch {
            throw it
        }
    }
}