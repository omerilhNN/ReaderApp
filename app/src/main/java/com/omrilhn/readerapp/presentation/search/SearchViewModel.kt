package com.omrilhn.readerapp.presentation.search

import androidx.lifecycle.ViewModel
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor():ViewModel(){
    private val _searchQueryState = MutableStateFlow(StandardTextFieldState())
    val searchQueryState: StateFlow<StandardTextFieldState> = _searchQueryState

    fun setSearchText(query:String){
        _searchQueryState.update {currentState->
            currentState.copy(text = query)
        }
    }

}