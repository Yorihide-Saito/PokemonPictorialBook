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
        // TODO: fetchPokemonSpecies
    }
}