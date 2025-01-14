package com.example.pokmonpictorialbook.features.ui.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokmonpictorialbook.domain.usecase.FetchAndInsertPokemonListUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonListFromDataBaseUseCase

class PokemonListViewModelFactory(
    private val fetchPokemonListFromDataBaseUseCase: FetchPokemonListFromDataBaseUseCase,
    private val fetchAndInsertPokemonListUseCase: FetchAndInsertPokemonListUseCase,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PokemonListViewModel(
            fetchPokemonListFromDataBaseUseCase,
            fetchAndInsertPokemonListUseCase
        ) as T
    }
}