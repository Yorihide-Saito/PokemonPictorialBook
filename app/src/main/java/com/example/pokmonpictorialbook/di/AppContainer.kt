package com.example.pokmonpictorialbook.di

import android.content.Context
import com.example.pokmonpictorialbook.data.repository.PokemonRepositoryImpl
import com.example.pokmonpictorialbook.di.AppModule.provideLocalImageDataSource
import com.example.pokmonpictorialbook.di.AppModule.providePokemonDatabase
import com.example.pokmonpictorialbook.di.AppModule.providePokemonApiService
import com.example.pokmonpictorialbook.di.AppModule.provideRemoteImageDataSource
import com.example.pokmonpictorialbook.domain.usecase.FetchAndInsertPokemonDetailUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonDetailUseCase
import com.example.pokmonpictorialbook.domain.usecase.FetchAndInsertPokemonListUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonDetailFromDataBaseUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonListFromDataBaseUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonListUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.InsertPokemonDetailUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.InsertPokemonListUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.image.DownloadImageUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.image.FetchImageUseCase

object AppContainer {
    private lateinit var applicationContext: Context

    fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }

    // Service
    private val pokemonApiService by lazy {
        providePokemonApiService()
    }

    // DAO
    private val pokemonDao by lazy {
        providePokemonDatabase(applicationContext).pokemonDao()
    }

    // DataSource
    private val localImageDataSource by lazy {
        provideLocalImageDataSource(applicationContext)
    }

    private val remoteImageDataSource by lazy {
        provideRemoteImageDataSource(applicationContext)
    }

    // Repository
    private val pokemonRepository by lazy {
        PokemonRepositoryImpl(
            pokemonApiService,
            pokemonDao,
            localImageDataSource,
            remoteImageDataSource
            )
    }

    // UseCase
    val fetchPokemonListUseCase by lazy {
        FetchPokemonListUseCase(pokemonRepository)
    }

    val fetchPokemonListFromDataBaseUseCase by lazy {
        FetchPokemonListFromDataBaseUseCase(pokemonRepository)
    }

    val insertPokemonListUseCase by lazy {
        InsertPokemonListUseCase(pokemonRepository)
    }

    val fetchAndInsertPokemonListUseCase by lazy {
        FetchAndInsertPokemonListUseCase(
            fetchPokemonListUseCase = fetchPokemonListUseCase,
            fetchPokemonListFromDataBaseUseCase = fetchPokemonListFromDataBaseUseCase,
            insertPokemonListUseCase = insertPokemonListUseCase
        )
    }

    val fetchPokemonDetailUseCase by lazy {
        FetchPokemonDetailUseCase(pokemonRepository)
    }

    val insertPokemonDetailUseCase by lazy {
        InsertPokemonDetailUseCase(pokemonRepository)
    }

    val downloadImageUseCase by lazy {
        DownloadImageUseCase(pokemonRepository)
    }

    val fetchImageUseCase by lazy {
        FetchImageUseCase(pokemonRepository)
    }

    val fetchPokemonDetailFromDataBaseUseCase by lazy {
        FetchPokemonDetailFromDataBaseUseCase(
            pokemonRepository =  pokemonRepository,
            fetchImageUseCase = fetchImageUseCase
        )
    }

    val fetchAndInsertPokemonDetailUseCase by lazy {
        FetchAndInsertPokemonDetailUseCase(
            fetchPokemonDetailUseCase = fetchPokemonDetailUseCase,
            insertPokemonDetailUseCase = insertPokemonDetailUseCase,
            downloadImageUseCase = downloadImageUseCase,
            fetchPokemonDetailFromDataBaseUseCase = fetchPokemonDetailFromDataBaseUseCase
        )
    }
}