package com.example.pokmonpictorialbook.domain.usecase.common

import android.graphics.Bitmap
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import com.example.pokmonpictorialbook.domain.usecase.common.image.FetchImageUseCase
import com.example.pokmonpictorialbook.features.ui.model.PokemonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale

class FetchPokemonDetailFromDataBaseUseCase(
    private val pokemonRepository: PokemonRepository,
    private val fetchImageUseCase: FetchImageUseCase,
    private val languageCode: String = Locale.getDefault().language
) {
    operator fun invoke(pokemonId: Int): Flow<PokemonDetail> {
        return flow {
            val pokemonDetailEntity: PokemonDetailEntity? = pokemonRepository.fetchPokemonDetailFromDatabase(pokemonId)
            val pokemonNameEntity: PokemonNameEntity? = pokemonRepository.fetchPokemonNameWithTranslationFromDatabase(pokemonId, languageCode)
            val pokemonGeneraEntity: PokemonGeneraEntity? = pokemonRepository.fetchPokemonGeneraWithTranslationFromDatabase(pokemonId, languageCode)
            val pokemonFlavorTextEntity: PokemonFlavorTextEntity? = pokemonRepository.fetchPokemonFlavorTextWithTranslationFromDatabase(pokemonId, languageCode)

            val pairImage: Pair<Bitmap?, Bitmap?> = fetchImageUseCase(pokemonDetailEntity).first()
            emit(
                PokemonDetail(
                    id = pokemonDetailEntity?.pokemonId,
                    name = pokemonNameEntity?.name,
                    types = pokemonDetailEntity?.types ?: listOf("?????"),
                    height = pokemonDetailEntity?.height,
                    weight = pokemonDetailEntity?.weight,
                    genera = pokemonGeneraEntity?.genera,
                    flavorText = pokemonFlavorTextEntity?.flavorText,
                    frontImageBitmap = pairImage.first,
                    backImageBitmap = pairImage.second
                )
            )
        }.flowOn(Dispatchers.IO).catch {
            throw it
        }
    }

}