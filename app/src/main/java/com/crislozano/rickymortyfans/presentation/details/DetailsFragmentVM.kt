package com.crislozano.rickymortyfans.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crislozano.rickymortyfans.domain.usecase.GetSingleCharacterUC
import com.crislozano.rickymortyfans.presentation.commons.State
import kotlinx.coroutines.launch

class DetailsFragmentVM(private val getSingleCharacterUC: GetSingleCharacterUC): ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    fun loadData(id: Int) {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = getSingleCharacterUC.invoke(id)
            if(result.isSuccessful && result.body() != null) {
                _state.value = State.Success(result.body()!!)
            } else {
                _state.value = State.Error("Something went wrong")
            }
        }
    }
}