package com.crislozano.rickymortyfans.domain.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class [Location] represents the response from the API. Location of the character.
 */

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)