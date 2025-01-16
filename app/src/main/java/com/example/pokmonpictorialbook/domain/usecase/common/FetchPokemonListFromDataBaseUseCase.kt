package com.example.pokmonpictorialbook.domain.usecase.common

import android.graphics.Bitmap
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import com.example.pokmonpictorialbook.ui.feature.model.Pokemon
import com.example.pokmonpictorialbook.ui.feature.model.PokemonListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale

class FetchPokemonListFromDataBaseUseCase(
    private val repository: PokemonRepository,
    private val languageCode: String = Locale.getDefault().language
) {
    operator fun invoke(): Flow<PokemonListData> {
        return flow{
            val pokemonEntityList: List<PokemonEntity?> = repository.fetchAllPokemonFromDatabase()
            val pokemonDetailEntityList: List<PokemonDetailEntity?> = repository.fetchAllPokemonDetailFromDatabase()
            emit(
                PokemonListData(
                    numberOfAllPokemons = pokemonDetailEntityList.count(),
                    // TODO これ以降は listPokemonListResponse が影響しない箇所なのでいい感じにしたい。
                    pokemonDetailList = pokemonEntityList.map { pokemon ->
                        val pokemonId: Int = pokemon?.id ?: 0
                        Pokemon(
                            id = pokemonId,
                            name = pokemon?.name,
                            name_translation = getPokemonNameFindEntityByPokemonId(pokemonDetailEntityList, pokemonId) ?: "?????",
                            types = getPokemonTypes(pokemonDetailEntityList, pokemonId),
                            iconBitmap = getPokemonImageFindEntityByPokemonId(pokemonDetailEntityList, pokemonId)
                        )
                    },
                )
            )
        }.flowOn(Dispatchers.IO).catch {
            throw it
        }
    }

    private fun getPokemonTypes(pokemonDetailEntityList: List<PokemonDetailEntity?>, pokemonId: Int): List<String> {
        val pokemonEntity = pokemonDetailEntityList.find { it?.pokemonId == pokemonId }
        return pokemonEntity?.types?.map { it ?: "??????" } ?: listOf("?????")
    }

    suspend fun getPokemonNameFindEntityByPokemonId(pokemonDetailEntityList: List<PokemonDetailEntity?>, pokemonId: Int): String? {
        return if (pokemonDetailEntityList.any { it?.pokemonId == pokemonId }) {
            repository.fetchPokemonNameWithTranslationFromDatabase(pokemonId, languageCode)?.name
        } else {
            null
        }
    }

    suspend fun getPokemonImageFindEntityByPokemonId(pokemonDetailEntityList: List<PokemonDetailEntity?>, pokemonId: Int): Bitmap? {
        return if (pokemonDetailEntityList.any { it?.pokemonId == pokemonId }) {
            repository.fetchImage("front_image_$pokemonId")
        } else {
            null
        }
    }
}