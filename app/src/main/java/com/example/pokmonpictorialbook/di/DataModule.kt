package com.example.pokmonpictorialbook.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import com.example.pokmonpictorialbook.data.api.PokemonApiService
import com.example.pokmonpictorialbook.data.database.PokemonDatabase
import com.example.pokmonpictorialbook.data.common.PokemonMapper
import com.example.pokmonpictorialbook.data.source.LocalImageDataSource
import com.example.pokmonpictorialbook.data.source.RemoteImageDataSource
import com.example.pokmonpictorialbook.features.ui.detail.viewmodel.PokemonDetailViewModel
import com.example.pokmonpictorialbook.features.ui.list.viewmodel.PokemonListViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    fun providePokemonApiService(): PokemonApiService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PokemonApiService::class.java)
    }

    fun providePokemonDatabase(context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_db"
        ).build()
    }

    fun provideLocalImageDataSource(context: Context): LocalImageDataSource {
        return LocalImageDataSource(context)
    }

    fun provideRemoteImageDataSource(context: Context): RemoteImageDataSource {
        return RemoteImageDataSource()
    }

    fun providePokemonListViewModel(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return PokemonListViewModel(
                    AppContainer.fetchPokemonListFromDataBaseUseCase,
                    AppContainer.fetchAndInsertPokemonListUseCase
                ) as T
            }
        }
    }

    fun providePokemonDetailViewModel(pokemonName: String): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return PokemonDetailViewModel(
                    AppContainer.fetchAndInsertPokemonDetailUseCase,
                    pokemonName
                ) as T
            }
        }
    }

    fun providePokemonMapper(context: Context): PokemonMapper {
        return PokemonMapper()
    }
}