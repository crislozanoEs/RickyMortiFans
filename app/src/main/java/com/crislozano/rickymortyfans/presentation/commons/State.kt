package com.crislozano.rickymortyfans.presentation.commons

import com.crislozano.rickymortyfans.domain.usecase.GetSingleCharacterUC

/**
 * Class [State] that represents the state of the UI.
 */

sealed class State {
    data object Loading : State()
    data class Success(val data: Any) : State()
    data class Error(val message: String) : State()

}