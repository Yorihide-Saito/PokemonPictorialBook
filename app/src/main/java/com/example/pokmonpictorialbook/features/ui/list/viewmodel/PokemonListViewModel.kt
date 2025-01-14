package com.example.pokmonpictorialbook.features.ui.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokmonpictorialbook.domain.usecase.FetchAndInsertPokemonListUseCase
import com.example.pokmonpictorialbook.domain.usecase.common.FetchPokemonListFromDataBaseUseCase
import com.example.pokmonpictorialbook.features.ui.model.PokemonListData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed interface PokemonListUiState {
    data object Loading : PokemonListUiState
    data object Error : PokemonListUiState
    data class Success(val pokemonListData: PokemonListData) : PokemonListUiState
}

class PokemonListViewModel(
    private val fetchPokemonListFromDataBaseUseCase: FetchPokemonListFromDataBaseUseCase,
    private val fetchAndInsertPokemonListUseCase: FetchAndInsertPokemonListUseCase
) : ViewModel() {
    private val _pokemonListUiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val pokemonListUiState: StateFlow<PokemonListUiState> = _pokemonListUiState

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            fetchAndInsertPokemonListUseCase()
                .onStart {
                    _pokemonListUiState.value = PokemonListUiState.Loading
                }
                .catch {
                    _pokemonListUiState.value = PokemonListUiState.Error
                }
                .collect { result ->
                    _pokemonListUiState.value = PokemonListUiState.Success(result)
                }
        }
    }

    fun onScreenRevisited() {
        reloadPokemon()
    }

    private fun reloadPokemon() {
        viewModelScope.launch {
            // TODO ViewModel にロジックが乗っているのと、毎回APIリクエストが叩かれるので、別のUseCaseとして実装する。
            fetchPokemonListFromDataBaseUseCase()
                .filterNotNull()
                .collect { result ->
                    val currentState = _pokemonListUiState.value
                    if (currentState is PokemonListUiState.Success) {
                        val currentPokemonListData = currentState.pokemonListData
                        // FIXME ここコード読みづらいから直したい
                        val updatedPokemonList = if (
                                currentPokemonListData.pokemonDetailList == result.pokemonDetailList
                            ) {
                                currentPokemonListData.pokemonDetailList
                            } else {
                                result.pokemonDetailList
                            }
                        val updatedPokemonListData =
                            currentPokemonListData.copy(pokemonDetailList = updatedPokemonList)
                        _pokemonListUiState.value =
                            PokemonListUiState.Success(updatedPokemonListData)
                    }
                }
        }
    }
}