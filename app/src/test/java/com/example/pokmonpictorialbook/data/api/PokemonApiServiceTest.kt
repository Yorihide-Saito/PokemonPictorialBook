package com.example.pokmonpictorialbook.data.api

import com.example.pokmonpictorialbook.data.api.response.PokemonListResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonSpeciesResponse
import com.example.pokmonpictorialbook.data.api.response.TypeDetailResponse
import com.example.pokmonpictorialbook.data.api.response.TypeSlotResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class PokemonApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: PokemonApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(PokemonApiService::class.java)
    }

    @After
    fun teamDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchPokemonList_LimitAndOffsetNone_ReturnPokemonListResponse() {
        runTest {
            // もっと多いけどMockResponseなので減らしている
            val mockResponse: MockResponse = MockResponse()
                .setBody(
                    """
                        {
                            "count": 1302,
                            "next": null,
                            "previous": null,
                            "results": [
                              {
                                "name": "bulbasaur",
                                "url": "https://pokeapi.co/api/v2/pokemon/1/"
                              },
                              {
                                "name": "ivysaur",
                                "url": "https://pokeapi.co/api/v2/pokemon/2/"
                              },
                              {
                                "name": "venusaur",
                                "url": "https://pokeapi.co/api/v2/pokemon/3/"
                              }
                            ]
                    }
                    """.trimIndent()
                )
                .setResponseCode(200)
            mockWebServer.enqueue(mockResponse)

            val response: Response<PokemonListResponse> = apiService.fetchPokemonList()

            assertEquals(true, response.isSuccessful)
            response.body().let{
                assertEquals(1302, it?.count)
                it?.results?.get(0).let { it0 ->
                    assertEquals("bulbasaur", it0?.name)
                    assertEquals("https://pokeapi.co/api/v2/pokemon/1/", it0?.url)
                }
                it?.results?.get(1).let { it1 ->
                    assertEquals("ivysaur", it1?.name)
                    assertEquals("https://pokeapi.co/api/v2/pokemon/2/", it1?.url)
                }
                it?.results?.get(2).let { it2 ->
                    assertEquals("venusaur", it2?.name)
                    assertEquals("https://pokeapi.co/api/v2/pokemon/3/", it2?.url)
                }
            }
        }
    }

    @Test
    fun fetchPokemonList_ErrorResponse() {
        runTest{
            val mockResponse: MockResponse = MockResponse()
                .setResponseCode(404)
            mockWebServer.enqueue(mockResponse)

            val response: Response<PokemonListResponse> = apiService.fetchPokemonList()

            assertEquals(false, response.isSuccessful)
            assertEquals(404, response.code())
        }
    }

    @Test
    fun fetchPokemon_PokemonNameIsDitto_ReturnDittoPokemonResponse() {
        runTest {
            // もっと多いけどMockResponseなので減らしている
            val mockResponse: MockResponse = MockResponse()
                .setBody(
                    """
                        {
                            "id": 132,
                            "name": "ditto",
                            "height": 3,
                            "weight": 40,
                            "types": [
                                {
                                    "slot": 1,
                                    "type": {
                                        "name": "normal",
                                        "url": "https://pokeapi.co/api/v2/type/1/"
                                    }
                                }
                            ],
                            "sprites": {
                                "back_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
                                "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/front/132.png"
                            }
                        }
                    """.trimIndent()
                )
                .setResponseCode(200)

            mockWebServer.enqueue(mockResponse)

            val response: Response<PokemonResponse> = apiService.fetchPokemon("ditto")

            assertEquals(true, response.isSuccessful)
            response.body().let {
                assertEquals(132, it?.id)
                assertEquals("ditto", it?.name)
                assertEquals(3, it?.height)
                assertEquals(40, it?.weight)
                assertEquals(listOf(TypeSlotResponse(1, TypeDetailResponse("normal", "https://pokeapi.co/api/v2/type/1/"))), it?.types)
                assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png", it?.sprites?.backDefault)
                assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/front/132.png", it?.sprites?.frontDefault)
            }
        }
    }

    @Test
    fun fetchPokemon_ErrorResponse() {
        runTest{
            val mockResponse: MockResponse = MockResponse()
                .setResponseCode(404)
            mockWebServer.enqueue(mockResponse)

            val response: Response<PokemonResponse> = apiService.fetchPokemon("ditto")

            assertEquals(false, response.isSuccessful)
            assertEquals(404, response.code())
        }
    }

    @Test
    fun fetchPokemonSpecies_PokemonNameIsDitto_ReturnDittoPokemonSpeciesResponse() {
        runTest {
            val mockResponse: MockResponse = MockResponse()
                .setBody(
                    """
                    {
                        "id": 681,
                        "genera": [
                            {
                                "genus": "Royal Sword Pokémon",
                                "language": {
                                    "name": "en",
                                    "url": "https://pokeapi.co/api/v2/language/9/"
                                }
                            },
                            {
                                "genus": "おうけんポケモン",
                                "language": {
                                    "name": "ja",
                                    "url": "https://pokeapi.co/api/v2/language/11/"
                                }
                            }
                        ],
                        "flavor_text_entries": [
                            {
                                "flavor_text": "Apparently, it can detect the innate qualities\nof leadership. According to legend, whoever it\nrecognizes is destined to become king.",
                                "language": {
                                    "name": "en",
                                    "url": "https://pokeapi.co/api/v2/language/9/"
                                }
                            },
                            {
                                "flavor_text": "王の　素質を　持つ　人間を\n見抜くらしい。認められた　人は\nやがて　王になると　言われている。",
                                "language": {
                                    "name": "ja",
                                    "url": "https://pokeapi.co/api/v2/language/11/"
                                }
                            }
                        ],
                        "names": [
                            {
                                "language": {
                                    "name": "en",
                                    "url": "https://pokeapi.co/api/v2/language/9/"
                                },
                                "name": "Aegislash"
                            },
                            {
                                "language": {
                                    "name": "ja",
                                    "url": "https://pokeapi.co/api/v2/language/11/"
                                },
                                "name": "ギルガルド"
                            }
                        ]
                    }
                    """.trimIndent()
                )
                .setResponseCode(200)
            mockWebServer.enqueue(mockResponse)

            val response: Response<PokemonSpeciesResponse> = apiService.fetchPokemonSpecies("aegislash")

            assertEquals(true, response.isSuccessful)
            response.body().let {
                assertEquals(681, it?.id)
                // Genera
                it?.genera?.get(0).let { genera0 ->
                    assertEquals("Royal Sword Pokémon", genera0?.genus)
                    assertEquals("en", genera0?.language?.languageCode)
                    assertEquals("https://pokeapi.co/api/v2/language/9/", genera0?.language?.url)
                }
                it?.genera?.get(1).let { genera1 ->
                    assertEquals("おうけんポケモン", genera1?.genus)
                    assertEquals("ja", genera1?.language?.languageCode)
                    assertEquals("https://pokeapi.co/api/v2/language/11/", genera1?.language?.url)
                }
                // FlavorTextEntries
                it?.flavorTextEntries?.get(0).let { flavorTextEntries0 ->
                    assertEquals("Apparently, it can detect the innate qualities\nof leadership. According to legend, whoever it\nrecognizes is destined to become king.", flavorTextEntries0?.flavorText)
                    assertEquals("en", flavorTextEntries0?.language?.languageCode)
                }
                it?.flavorTextEntries?.get(1).let { flavorTextEntries1 ->
                    assertEquals("王の　素質を　持つ　人間を\n見抜くらしい。認められた　人は\nやがて　王になると　言われている。", flavorTextEntries1?.flavorText)
                    assertEquals("ja", flavorTextEntries1?.language?.languageCode)
                }
                // Name
                it?.names?.get(0).let { name0 ->
                    assertEquals("Aegislash", name0?.name)
                    assertEquals("en", name0?.language?.languageCode)
                }
                it?.names?.get(1).let { name1 ->
                    assertEquals("ギルガルド", name1?.name)
                    assertEquals("ja", name1?.language?.languageCode)
                }
            }
        }
    }

    @Test
    fun fetchPokemonSpecies_ErrorResponse() {
        runTest{
            val mockResponse: MockResponse = MockResponse()
                .setResponseCode(404)
            mockWebServer.enqueue(mockResponse)

            val response: Response<PokemonSpeciesResponse> = apiService.fetchPokemonSpecies("aegislash")

            assertEquals(false, response.isSuccessful)
            assertEquals(404, response.code())
        }
    }
}