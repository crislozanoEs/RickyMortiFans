package com.crislozano.rickymortyfans.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crislozano.rickymortyfans.domain.usecase.GetCharactersUC
import com.crislozano.rickymortyfans.presentation.commons.State
import kotlinx.coroutines.launch
import com.crislozano.rickymortyfans.domain.entities.Result

class ListFragmentVM(private val getCharactersUC: GetCharactersUC): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    var page: Int = 1
    var isLoading: Boolean = false
    var isLastPage: Boolean = false

    fun loadData() {
        _state.value = State.Loading
        viewModelScope.launch {
            val result = getCharactersUC.invoke(page)
            if(result.isSuccessful && result.body() != null) {
                page++
                _state.value = State.Success(result.body()?.results ?: emptyList<Result>())
                isLastPage = result.body()?.info?.next == null
            } else {
                _state.value = State.Error("Something went wrong")
            }
            isLoading = false
        }
    }

    fun loadMoreData(firstVisibleItems: Int, visibleItemCount: Int, totalItemCount: Int) {
        if(!isLoading && !isLastPage) {
            if(firstVisibleItems + visibleItemCount >=  totalItemCount) {
                loadNextPage()
            }
        }
    }

    private fun loadNextPage() {
        if(isLoading || isLastPage) return
        isLoading = true
        loadData()
    }

    fun resetPage() {
        page = 1
    }
}