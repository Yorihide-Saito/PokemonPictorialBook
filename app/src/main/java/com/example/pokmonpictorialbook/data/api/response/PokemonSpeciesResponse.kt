package com.example.pokmonpictorialbook.data.api.response

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse(
    val id: Int,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntries>,
    val genera: List<Genera>,
    val names: List<Name>
)

data class FlavorTextEntries(
    @SerializedName("flavor_text") val flavorText: String,
    val language: Language
)

data class Genera(
    val genus: String,
    val language: Language
)

data class Name(
    val language: Language,
    val name: String
)

data class Language(
    @SerializedName("name") val languageCode: String,
    val url: String
)