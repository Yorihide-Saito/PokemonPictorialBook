package com.example.pokmonpictorialbook.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pokmonpictorialbook.ui.navigation.AppNavGraph
import com.example.pokmonpictorialbook.ui.theme.PokémonPictorialBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokémonPictorialBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavGraph()
                    }
                }
            }
        }
//        lifecycleScope.launch {
//            val service = DataModule.providePokemonApiService()
//            val listResponse = service.getPokemonList()
//            Log.d("MainActivity", "Fetched: ${listResponse.results.size} pokemons")
//        }
    }
}