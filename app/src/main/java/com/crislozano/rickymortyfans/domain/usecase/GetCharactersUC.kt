package com.crislozano.rickymortyfans.domain.usecase

import com.crislozano.rickymortyfans.data.network.ApiServices
import com.crislozano.rickymortyfans.domain.entities.SingleCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case [GetSingleCharacterUC] that calls API to get all characters
 */

class GetCharactersUC(private val apiServices: ApiServices) {

    suspend fun invoke(page: Int) =
        withContext(Dispatchers.IO) {
            apiServices.getCharacters(page)
        }

}