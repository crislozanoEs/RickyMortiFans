package com.crislozano.rickymortyfans.data.network

import com.crislozano.rickymortyfans.domain.entities.Characters
import com.crislozano.rickymortyfans.domain.entities.SingleCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("character/")
    suspend fun getCharacters(@Query("page") query: Int): Response<Characters>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<SingleCharacter>

}