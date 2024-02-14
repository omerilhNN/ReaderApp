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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FireRepository

) : ViewModel() {
    private val _listOfBooks = MutableStateFlow<DataOrException<List<MBook>,Boolean,Exception>>(DataOrException())
    val listOfBooks: StateFlow<DataOrException<List<MBook>,Boolean,Exception>> get() = _listOfBooks.asStateFlow()

    private val _thoughtText = MutableStateFlow<String>("")
    val thoughtText:StateFlow<String> get() = _thoughtText.asStateFlow()

    //VALUES FOR UPDATE SCREEN -> Manage both screens VM
    //***************************************
    private val _isStartedReading = mutableStateOf(false)
    val isStartedReading:Boolean get() = _isStartedReading.value
    private val _isFinishedReading = mutableStateOf(false)
    val isFinishedReading:Boolean get() = _isFinishedReading.value

    // ***************************************


     val data: MutableState<DataOrException<List<MBook>,Boolean,Exception>>
        = mutableStateOf(DataOrException(listOf(),true,Exception("")))

    init{
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDatabase()
            if(!data.value.data.isNullOrEmpty()) {
                updateListOfBooks(data.value.data!!)
                data.value.loading = false
            }else {
                updateListOfBooks(emptyList())
                data.value.loading = false
            }
        }
        Log.d("GET","getAllBooksFromDatabase: ${data.value.data?.toList().toString()}")
    }
    fun updateListOfBooks(books:List<MBook>){
        _listOfBooks.update {currentState->
            currentState.copy(data = books)
        }
    }
    fun setThoughtText(thought:String){
        _thoughtText.value = thought
    }
    fun setIsFinishedReading(){
        _isFinishedReading.value = !_isFinishedReading.value
    }
    fun setIsReadingStarted(){
        _isStartedReading.value = !_isStartedReading.value
    }
}