package com.example.pokmonpictorialbook.domain.usecase.common.image

import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DownloadImageUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(pokemonDetailEntity: PokemonDetailEntity) {
        // 画像のローカルへの保存
        runBlocking(Dispatchers.IO) {
            pokemonDetailEntity.apply {
                downloadImages(
                    pokemonId = pokemonId,
                    frontImageUrl = frontDefault,
                    backImageRul = backDefault
                )
            }
        }
    }

    suspend fun downloadImages(
        pokemonId: Int,
        frontImageUrl: String,
        backImageRul: String
    ) {
        pokemonRepository.downloadImage(imageName = "front_image_$pokemonId", url = frontImageUrl)
        pokemonRepository.downloadImage(imageName = "back_image_$pokemonId", url = backImageRul)
    }
}