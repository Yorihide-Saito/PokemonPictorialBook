package com.example.pokmonpictorialbook.data.api.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlotResponse>,
    val sprites: SpritesResponse
)

data class TypeSlotResponse(
    val slot: Int,
    val type: TypeDetailResponse
)

data class TypeDetailResponse(
    val name: String,
    val url: String
)

data class SpritesResponse(
    @SerializedName("back_default") val backDefault: String,
    @SerializedName("front_default") val frontDefault: String
)