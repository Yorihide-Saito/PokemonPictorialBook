package com.example.pokmonpictorialbook.domain.usecase.common.image

import android.graphics.Bitmap
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchImageUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(pokemonDetailEntity: PokemonDetailEntity?): Flow<Pair<Bitmap?, Bitmap?>> {
        // ローカルの画像を取り出す
        return flow {
            emit(fetchImages(pokemonId = pokemonDetailEntity?.pokemonId ?: 0))
        }.flowOn(Dispatchers.IO).catch {
            throw it
        }
    }

    private suspend fun fetchImages(
        pokemonId: Int
    ): Pair<Bitmap?, Bitmap?> {
        return Pair(
            pokemonRepository.fetchImage(imageName = "front_image_$pokemonId"),
            pokemonRepository.fetchImage(imageName = "back_image_$pokemonId")
        )
    }
}