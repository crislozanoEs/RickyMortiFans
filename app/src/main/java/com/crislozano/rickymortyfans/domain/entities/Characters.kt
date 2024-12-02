package com.crislozano.rickymortyfans.domain.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class [Characters] represents the response from the API. List of characters
 */
data class Characters (
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Result>
)