package com.omrilhn.readerapp.presentation.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omrilhn.readerapp.core.domain.states.StandardTextFieldState
import com.omrilhn.readerapp.data.repository.BookRepository
import com.omrilhn.readerapp.utils.Resource
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
    private val _searchBookState = MutableStateFlow<SearchState>(SearchState())
    val searchBookState: StateFlow<SearchState> = _searchBookState.asStateFlow()
//I have set isLoading parameter into SearchState so it is easy to control by State

//    private var _isLoading: MutableState<Boolean> = mutableStateOf(true)
//    var isLoading: Boolean by _isLoading

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

            try{
                when(val response = bookRepository.getBooks(query)) {
                    is Resource.Success -> {
                        //if GETTING BOOKS is successfull then assign that response to listOfBooks element
                        //of searchState - which is the SSOT of our Search logic
                      _searchBookState.update { currentState->
                          currentState.copy(listOfBooks = response.data!!)
                      }
                        if (_searchBookState.value.listOfBooks.isNotEmpty())
                            _searchBookState.value.isLoading = false
                    }

                    is Resource.Error -> {
                        _searchBookState.value.isLoading = false

                        Log.e("Network", "Search books: failed getting books")
                    }
                    is Resource.Loading -> {
                        _searchBookState.value.isLoading = true

                        Log.d("Network", "Search books: Loading books...")
                    }

                }

            }catch(e:Exception){
                _searchBookState.value.isLoading = false
                Log.d("Network","searchBooks: ${e.message.toString()}")
            }

        }
    }

}