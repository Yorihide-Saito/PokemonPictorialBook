package com.example.pokmonpictorialbook.ui.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokmonpictorialbook.domain.usecase.FetchAndInsertPokemonDetailUseCase
import com.example.pokmonpictorialbook.ui.feature.model.PokemonDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed interface PokemonDetailUiState {
    data object Loading : PokemonDetailUiState
    data object Error : PokemonDetailUiState
    data class Success(val pokemonDetail: PokemonDetail) : PokemonDetailUiState
}

class PokemonDetailViewModel(
    private val fetchAndInsertPokemonDetailUseCase: FetchAndInsertPokemonDetailUseCase,
    private val pokemonName: String
) : ViewModel() {
    private val _pokemonDetailUiState: MutableStateFlow<PokemonDetailUiState> = MutableStateFlow(
        PokemonDetailUiState.Loading
    )
    val pokemonDetailUiState: StateFlow<PokemonDetailUiState> = _pokemonDetailUiState.asStateFlow()

    init {
        loadPokemonDetail(pokemonName)
    }

   private fun loadPokemonDetail(pokemonName: String) {
        viewModelScope.launch {
            fetchAndInsertPokemonDetailUseCase(pokemonName)
                .onStart {
                    _pokemonDetailUiState.value = PokemonDetailUiState.Loading
                }
                .catch {
                    _pokemonDetailUiState.value = PokemonDetailUiState.Error
                }
                .collect { result ->
                    _pokemonDetailUiState.value = PokemonDetailUiState.Success(result)
                }
        }
    }
}