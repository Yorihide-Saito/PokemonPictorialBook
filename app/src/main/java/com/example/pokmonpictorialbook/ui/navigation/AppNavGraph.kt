package com.example.pokmonpictorialbook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokmonpictorialbook.di.AppModule.providePokemonDetailViewModel
import com.example.pokmonpictorialbook.di.AppModule.providePokemonListViewModel
import com.example.pokmonpictorialbook.ui.feature.detail.PokemonDetailScreen
import com.example.pokmonpictorialbook.ui.feature.detail.viewmodel.PokemonDetailViewModel
import com.example.pokmonpictorialbook.ui.feature.list.PokemonListScreen
import com.example.pokmonpictorialbook.ui.feature.list.viewmodel.PokemonListViewModel

sealed class AppDestinations(val route: String) {
    data object PokemonList : AppDestinations("pokemon_list")
    data object PokemonDetail : AppDestinations("pokemon_detail/{pokemonName}") {
        fun createRoute(name: String): String {
            return "pokemon_detail/$name"
        }
    }
}

@Composable
fun AppNavGraph(
    startDestination: String = AppDestinations.PokemonList.route
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = AppDestinations.PokemonList.route,
        ) { backStackEntry ->
            val viewModel: PokemonListViewModel = viewModel(
                key = "PokemonListViewModel",
                factory = providePokemonListViewModel()
            )
            PokemonListScreen(
                onItemClick = { name: String ->
                    navController.navigate(AppDestinations.PokemonDetail.createRoute(name)) {
                        launchSingleTop = true
                        popUpTo(AppDestinations.PokemonList.route) {
                            inclusive = false
                        }
                    }
                },
                viewModel = viewModel
            )
        }
        composable(
            route = AppDestinations.PokemonDetail.route,
            // TODO pokemonName は共通化したい。
            arguments = listOf(navArgument("pokemonName") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val pokemonName: String = backStackEntry.arguments?.getString("pokemonName").toString()
            val viewModel: PokemonDetailViewModel = viewModel(
                key = "PokemonDetailViewModel",
                factory = providePokemonDetailViewModel(pokemonName = pokemonName)
            )
            PokemonDetailScreen(
                viewModel = viewModel
            )
        }
    }
}