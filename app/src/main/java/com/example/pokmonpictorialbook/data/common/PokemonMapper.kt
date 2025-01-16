package com.example.pokmonpictorialbook.data.common

import com.example.pokmonpictorialbook.data.api.response.PokemonListResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonSpeciesResponse
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonSpeciesEntity

class PokemonMapper {
    fun mapPokemonResponseToPokemonDetailEntity(response: PokemonResponse): PokemonDetailEntity {
        return PokemonDetailEntity(
            pokemonId = response.id,
            height = response.height,
            weight = response.weight,
            types = response.types.map { it.type.name },
            backDefault = response.sprites.backDefault,
            frontDefault = response.sprites.frontDefault
        )
    }

    fun mapPokemonListResponseToEntity(
        response: PokemonListResponse,
    ): List<PokemonEntity>{
        return response.results.map { result ->
            PokemonEntity(
                id = extractListNumberFromUrl(result.url)?.toInt() ?: 0,
                name = result.name,
            )
        }
    }

    // 文字列の末端の数字を取得する。
    // TODO: もう少しいい感じの正規表現使用できそうだが動くから一旦無視
    private fun extractListNumberFromUrl(url: String): String? {
        val regex = """(\d+)/?$""".toRegex()
        val matchResult = regex.find(url)
        return matchResult?.groupValues?.get(1)
    }

    fun mapPokemonSpeciesResponseToEntity(
        response: PokemonSpeciesResponse
    ): PokemonSpeciesEntity {
        val pokemonId: Int = response.id
        return PokemonSpeciesEntity(
            response.names.map { name ->
                PokemonNameEntity(
                    pokemonId = pokemonId,
                    languageCode = name.language.languageCode,
                    name = name.name
                )
            },
            response.flavorTextEntries.map { flavorText ->
                PokemonFlavorTextEntity(
                    pokemonId = pokemonId,
                    languageCode = flavorText.language.languageCode,
                    flavorText = flavorText.flavorText
                )
            },
            response.genera.map { genus ->
                PokemonGeneraEntity(
                    pokemonId = pokemonId,
                    languageCode = genus.language.languageCode,
                    genera = genus.genus
                )
            }
        )
    }
}