package com.omrilhn.readerapp.presentation.search

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val bookRepository: BookRepository):ViewModel(){
    private val _searchQueryState = MutableStateFlow(StandardTextFieldState())
    val searchQueryState: StateFlow<StandardTextFieldState> = _searchQueryState

    //Having READ accesibility to _listOfBooks by asStateFlow
    private val _listOfBooks = MutableStateFlow(DataOrException<List<Item>,Boolean,Exception>())
    val listOfBooks: StateFlow<DataOrException<List<Item>,Boolean,Exception>> = _listOfBooks.asStateFlow()



    init{
        searchBooks("android")
    }

    fun setSearchText(query:String){
        _searchQueryState.update {currentState->
            currentState.copy(text = query)
        }
    }
    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) return@launch

            _listOfBooks.value.loading = true
            _listOfBooks.value = bookRepository.getBooks(query)
            Log.d("Search","searchBooks: ${_listOfBooks.value.data.toString()}")
            if(_listOfBooks.value.data.toString().isNotEmpty())
                _listOfBooks.value.loading = false
        }
    }

}