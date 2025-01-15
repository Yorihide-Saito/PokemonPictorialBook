package com.example.pokmonpictorialbook.data.api

import com.example.pokmonpictorialbook.data.api.response.PokemonListResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonResponse
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

            val response: PokemonListResponse = apiService.fetchPokemonList()

            assertEquals(1302, response.count)
            assertEquals("bulbasaur", response.results[0].name)
            assertEquals("https://pokeapi.co/api/v2/pokemon/1/", response.results[0].url)
            assertEquals("venusaur", response.results[2].name)
            assertEquals("https://pokeapi.co/api/v2/pokemon/3/", response.results[2].url)
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

            val response: PokemonResponse = apiService.fetchPokemon("ditto")

            assertEquals(132, response.id)
            assertEquals("ditto", response.name)
            assertEquals(3, response.height)
            assertEquals(40, response.weight)
            assertEquals(listOf(TypeSlotResponse(1, TypeDetailResponse("normal", "https://pokeapi.co/api/v2/type/1/"))), response.types)
            assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png", response.sprites.backDefault)
            assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/front/132.png", response.sprites.frontDefault)
        }
    }

    @Test
    fun fetchPokemonSpecies_PokemonNameIsDitto_ReturnDittoPokemonSpeciesResponse() {
        // TODO: fetchPokemonSpecies
    }
}