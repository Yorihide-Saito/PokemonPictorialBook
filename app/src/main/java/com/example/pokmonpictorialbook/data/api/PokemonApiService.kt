package com.example.pokmonpictorialbook.data.api

import com.example.pokmonpictorialbook.data.api.response.PokemonListResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 10000,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{pokemonName}")
    suspend fun fetchPokemon(
        @Path("pokemonName") pokemonName: String
    ): PokemonResponse

    @GET("pokemon-species/{pokemonName}")
    suspend fun fetchPokemonSpecies(
        @Path("pokemonName") pokemonName: String
    ): PokemonSpeciesResponse
}