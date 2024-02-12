package com.omrilhn.readerapp.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.data.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FireRepository

) : ViewModel() {
    private val _listOfBooks = MutableStateFlow<DataOrException<List<MBook>,Boolean,Exception>>(DataOrException())
    val listOfBooks: StateFlow<DataOrException<List<MBook>,Boolean,Exception>> get() = _listOfBooks.asStateFlow()


    val data: MutableState<DataOrException<List<MBook>,Boolean,Exception>>
        = mutableStateOf(DataOrException(listOf(),true,Exception("")))

    init{
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDatabase()
            if(!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
        Log.d("GET","getAllBooksFromDatabase: ${data.value.data?.toList().toString()}")
    }
    fun updateListOfBooks(books:List<MBook>){
        _listOfBooks.value.data = books
    }
}