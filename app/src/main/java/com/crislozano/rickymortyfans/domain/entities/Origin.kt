package com.crislozano.rickymortyfans.domain.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class [Origin] represents the response from the API. Origin of the character.
 */

data class Origin (
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)