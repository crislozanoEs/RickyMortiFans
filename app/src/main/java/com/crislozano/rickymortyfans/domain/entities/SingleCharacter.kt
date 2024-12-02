package com.crislozano.rickymortyfans.domain.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class [SingleCharacter] represents the response from the API. Detailed data of the Character
 */

data class SingleCharacter(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("created")
    val created: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("location")
    val location: Location,
)