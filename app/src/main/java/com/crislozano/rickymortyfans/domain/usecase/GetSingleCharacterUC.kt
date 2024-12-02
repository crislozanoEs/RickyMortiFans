package com.crislozano.rickymortyfans.domain.usecase

import com.crislozano.rickymortyfans.data.network.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case [GetSingleCharacterUC] that calls API to get details of a character
 */

class GetSingleCharacterUC(private val apiServices: ApiServices) {

    suspend fun invoke(id: Int) =
        withContext(Dispatchers.IO) {
            apiServices.getCharacter(id)
        }

}