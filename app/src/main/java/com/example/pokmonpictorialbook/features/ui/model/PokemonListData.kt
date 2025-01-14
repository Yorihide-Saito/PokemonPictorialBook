package com.example.pokmonpictorialbook.features.ui.model

import android.graphics.Bitmap

// ListScreen 用
data class PokemonListData(
    val numberOfAllPokemons: Int,
    val pokemonDetailList: List<Pokemon>
)

data class Pokemon(
    val id: Int?,
    val name: String?,
    val name_translation: String?,
    val types: List<String?>,
    val iconBitmap: Bitmap? //
)


// DetailScreen 用
data class PokemonDetail(
    val id: Int?,
    val name: String?,
    val types: List<String?>,
    val height: Int?,
    val weight: Int?,
    val genera: String?,
    val flavorText: String?,
    val frontImageBitmap: Bitmap?,
    val backImageBitmap: Bitmap?
)


// TODO Type クラスは使うか迷い中
data class Type(
    val type: String = "?????"
)
