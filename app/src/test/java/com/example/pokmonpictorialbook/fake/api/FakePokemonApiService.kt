package com.example.pokmonpictorialbook.fake.api

import com.example.pokmonpictorialbook.data.api.PokemonApiService
import com.example.pokmonpictorialbook.data.api.response.FlavorTextEntries
import com.example.pokmonpictorialbook.data.api.response.Genera
import com.example.pokmonpictorialbook.data.api.response.Language
import com.example.pokmonpictorialbook.data.api.response.Name
import com.example.pokmonpictorialbook.data.api.response.Pokemon
import com.example.pokmonpictorialbook.data.api.response.PokemonListResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonSpeciesResponse
import com.example.pokmonpictorialbook.data.api.response.SpritesResponse
import com.example.pokmonpictorialbook.data.api.response.TypeDetailResponse
import com.example.pokmonpictorialbook.data.api.response.TypeSlotResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakePokemonApiService : PokemonApiService {
    private var shouldReturnError: Boolean = false

    // PokemonResponse 用の Fake クラス
    val fakePokemonResponse = mapOf(
        Pair(
            "hoge",
            PokemonResponse(
                id = 1,
                name = "hoge",
                height = 2,
                weight = 3,
                types = listOf(
                    TypeSlotResponse(
                        slot = 1,
                        type = TypeDetailResponse(
                            name = "hoge_1",
                            url = "hogehoge_1"
                        )
                    ),
                    TypeSlotResponse(
                        slot = 2,
                        type = TypeDetailResponse(
                            name = "hoge_2",
                            url = "hogehoge_2"
                        )
                    )
                ),
                sprites = SpritesResponse(
                    backDefault = "hogeBack",
                    frontDefault = "hogeFront"
                )
            )
        ),
        Pair(
            "fuga",
            PokemonResponse(
                id = 11,
                name = "fuga",
                height = 22,
                weight = 33,
                types = listOf(
                    TypeSlotResponse(
                        slot = 1,
                        type = TypeDetailResponse(
                            name = "fuga_1",
                            url = "fugafuga_1"
                        )
                    ),
                    TypeSlotResponse(
                        slot = 2,
                        type = TypeDetailResponse(
                            name = "fuga_2",
                            url = "fugafuga_2"
                        )
                    )
                ),
                sprites = SpritesResponse(
                    backDefault = "fugaBack",
                    frontDefault = "fugaFront"
                )
            )
        )
    )

    // PokemonSpeciesResponse 用の Fake クラス
    val fakePokemonSpeciesResponse = mapOf(
        Pair(
            "hoge",
            PokemonSpeciesResponse(
                id = 1,
                flavorTextEntries = listOf(
                    FlavorTextEntries(
                        flavorText = "hogehoge_jp",
                        language = Language(
                            languageCode = "jp",
                            url = "hoge_jp"
                        )
                    ),
                    FlavorTextEntries(
                        flavorText = "hogehoge_en",
                        language = Language(
                            languageCode = "en",
                            url = "hoge_en"
                        )
                    )
                ),
                genera = listOf(
                    Genera(
                        genus ="hogehoge",
                        language = Language(
                            languageCode = "jp",
                            url = "hoge_jp"
                        )
                    ),
                    Genera(
                        genus ="hogehogehoge",
                        language = Language(
                            languageCode = "en",
                            url = "hoge_en"
                        )
                    )
                ),
                names = listOf(
                    Name(
                        language = Language(
                            languageCode = "jp",
                            url = "hoge_jp"
                        ),
                        name = "hoge_jp"
                    ),
                    Name(
                        language = Language(
                            languageCode = "en",
                            url = "hoge_en"
                        ),
                        name = "hoge_en"
                    )
                )
            )
        ),
        Pair(
            "fuga",
            PokemonSpeciesResponse(
                id = 2,
                flavorTextEntries = listOf(
                    FlavorTextEntries(
                        flavorText = "fugafuga_jp",
                        language = Language(
                            languageCode = "jp",
                            url = "fuga_jp"
                        )
                    ),
                    FlavorTextEntries(
                        flavorText = "fugafuga_en",
                        language = Language(
                            languageCode = "en",
                            url = "fuga_en"
                        )
                    )
                ),
                genera = listOf(
                    Genera(
                        genus = "fugafuga",
                        language = Language(
                            languageCode = "jp",
                            url = "fuga_jp"
                        )
                    ),
                    Genera(
                        genus = "fugafugafuga",
                        language = Language(
                            languageCode = "en",
                            url = "fuga_en"
                        )
                    )
                ),
                names = listOf(
                    Name(
                        language = Language(
                            languageCode = "jp",
                            url = "fuga_jp"
                        ),
                        name = "fuga_jp"
                    ),
                    Name(
                        language = Language(
                            languageCode = "en",
                            url = "fuga_en"
                        ),
                        name = "fuga_en"
                    )
                )
            )
        )
    )

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun fetchPokemonList(limit: Int, offset: Int): Response<PokemonListResponse> {
        return if (shouldReturnError) {
            Response.error(500, "Error fetching data".toResponseBody(null))
        } else {
            Response.success(
                PokemonListResponse(
                    count = limit,
                    results = List(limit) { index->
                        Pokemon(
                            name = "pokemon$index",
                            url = "https://pokeapi.co/api/v2/pokemon/$index")
                    }
                )
            )
        }
    }

    /**
     * PokemonResponse のフェイクデータを返すメソッド
     *
     * @param pokemonName only "hoge" or "fuga"
     * @return Response<PokemonResponse>
     */
    override suspend fun fetchPokemon(pokemonName: String): Response<PokemonResponse> {
        return if (shouldReturnError) {
            Response.error(500, "Error fetching data".toResponseBody(null))
        } else {
            Response.success(
                fakePokemonResponse[pokemonName]
            )
        }
    }

    /**
     * PokemonSpeciesResponse のフェイクデータを返すメソッド
     *
     * @param pokemonName only "hoge" or "fuga"
     * @return Response<PokemonResponse>
     */
    override suspend fun fetchPokemonSpecies(pokemonName: String): Response<PokemonSpeciesResponse> {
        return if (shouldReturnError) {
            Response.error(500, "Error fetching data".toResponseBody(null))
        } else {
            Response.success(
                fakePokemonSpeciesResponse[pokemonName]
            )
        }
    }
}