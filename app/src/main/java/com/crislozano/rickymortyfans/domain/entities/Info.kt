package com.crislozano.rickymortyfans.domain.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class [Info] represents the response from the API. Metadata of API.
 */

data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: Any?
)