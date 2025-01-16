package com.example.pokmonpictorialbook.ui.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokmonpictorialbook.R
import com.example.pokmonpictorialbook.ui.feature.common.ErrorScreen
import com.example.pokmonpictorialbook.ui.feature.common.LoadingScreen
import com.example.pokmonpictorialbook.ui.feature.detail.viewmodel.PokemonDetailUiState
import com.example.pokmonpictorialbook.ui.feature.detail.viewmodel.PokemonDetailViewModel
import com.example.pokmonpictorialbook.ui.feature.model.PokemonDetail

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel
) {
    val pokemonDetailUiState by viewModel.pokemonDetailUiState.collectAsState()

    when(pokemonDetailUiState) {
        is PokemonDetailUiState.Loading -> {
            LoadingScreen()
        }
        is PokemonDetailUiState.Success -> {
            val pokemonDetail = (pokemonDetailUiState as PokemonDetailUiState.Success).pokemonDetail
            PokemonDetailBody(pokemonDetail)
        }
        is PokemonDetailUiState.Error -> {
            ErrorScreen({ }) // TODO エラー時の再実行処理を作成する。
        }
    }
}

@Composable
fun PokemonDetailBody(
    pokemonDetail: PokemonDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "No.${pokemonDetail.id} ${pokemonDetail.name ?: stringResource(R.string.unknown)}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = pokemonDetail.genera ?: stringResource(R.string.unknown),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        // 身長と体重
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailCard(
                title = stringResource(R.string.height),
                value = "${pokemonDetail.height?.toFloat()?.div(10.0)}m",
                modifier = Modifier.weight(1f))
            DetailCard(
                title = stringResource(R.string.weight),
                value = "${pokemonDetail.weight?.toFloat()?.div(10.0)}kg",
                modifier = Modifier.weight(1f)
            )
        }

        // タイプ
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pokemonDetail.types.forEachIndexed { index, type ->
                DetailCard(title = "${stringResource(R.string.type)} ${index + 1}", value = type ?: stringResource(R.string.unknown))
            }
        }

        // 画像
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            pokemonDetail.frontImageBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Front Image",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(end = 8.dp)
                )
            }
            pokemonDetail.backImageBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Back Image",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(start = 8.dp)
                )
            }
        }

        // 説明文
        Text(
            text = pokemonDetail.flavorText ?: stringResource(R.string.no_description),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun DetailCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleSmall
        )
    }
}