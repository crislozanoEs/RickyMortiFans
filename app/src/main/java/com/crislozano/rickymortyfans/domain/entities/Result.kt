package com.crislozano.rickymortyfans.domain.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class [Result] represents the response from the API. Data of the character.
 */

data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("url")
    val url: String,
)