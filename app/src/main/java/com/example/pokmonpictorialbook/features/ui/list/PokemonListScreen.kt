package com.example.pokmonpictorialbook.features.ui.list

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokmonpictorialbook.R
import com.example.pokmonpictorialbook.features.ui.common.ErrorScreen
import com.example.pokmonpictorialbook.features.ui.common.LoadingScreen
import com.example.pokmonpictorialbook.features.ui.model.PokemonListData
import com.example.pokmonpictorialbook.features.ui.list.viewmodel.PokemonListUiState
import com.example.pokmonpictorialbook.features.ui.list.viewmodel.PokemonListViewModel
import com.example.pokmonpictorialbook.features.ui.model.Pokemon

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pokemonListUiState by viewModel.pokemonListUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onScreenRevisited()
    }

    when(pokemonListUiState) {
        is PokemonListUiState.Loading -> {
            LoadingScreen()
        }
        is PokemonListUiState.Success -> {
            val pokemonListData: PokemonListData = (pokemonListUiState as PokemonListUiState.Success).pokemonListData
            LazyColumn {
                // 欲しい情報なし
                items(pokemonListData.pokemonDetailList) { pokemon ->
                    PokemonListButton(
                        pokemon =  pokemon,
                        onItemClick = onItemClick
                    )
                }
            }
        }
        is PokemonListUiState.Error -> {
            ErrorScreen({ }) // TODO エラー時の再実行処理を作成する。
        }
    }
}

// TODO もう少し分割する
@Composable
fun PokemonListButton(
    pokemon: Pokemon,
    onItemClick: (String) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick(pokemon.name ?: "?????") }
            .padding(8.dp)
    ) {
        if (pokemon.iconBitmap != null) {
            Image(
                bitmap = pokemon.iconBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.poke_ball),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Row {
                Text(
                    text = "No.${pokemon.id}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${pokemon.name_translation}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                )
            }
            Row {
                pokemon.types.map {
                    Text(
                        text = getTypeName(context, it),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

/**
 *　タイプの言語言語対応のため
 */
fun getTypeName(context: Context, type: String?): String {
    val resourceId = context.resources.getIdentifier("type_$type", "string", context.packageName)
    return if (resourceId != 0) {
        context.getString(resourceId)
    } else {
        "?????" // TODO これもリソースファイルにする。
    }
}