package com.example.pokmonpictorialbook.features.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokmonpictorialbook.domain.usecase.FetchAndInsertPokemonDetailUseCase

class PokemonDetailViewModelFactory(
    private val fetchAndInsertPokemonDetailUseCase: FetchAndInsertPokemonDetailUseCase,
    private val pokemonName: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PokemonDetailViewModel(fetchAndInsertPokemonDetailUseCase, pokemonName) as T
    }
}