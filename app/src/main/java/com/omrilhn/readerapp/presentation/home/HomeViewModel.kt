package com.omrilhn.readerapp.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.data.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FireRepository

) : ViewModel() {
    private val _listOfBooks = MutableStateFlow<DataOrException<List<MBook>,Boolean,Exception>>(DataOrException())
    val listOfBooks: StateFlow<DataOrException<List<MBook>,Boolean,Exception>> get() = _listOfBooks.asStateFlow()
    private val _addedBooks = MutableStateFlow<DataOrException<List<MBook>,Boolean,Exception>>(
      DataOrException(
            data = listOfBooks.value.data?.filter {
                it.startedReading == null && it.finishedReading == null
            } ?: emptyList(),
        loading = listOfBooks.value.loading,
        Exception("")
    ))
    val addedBooks:StateFlow<DataOrException<List<MBook>,Boolean,Exception>> get() =_addedBooks

    private val _thoughtText = MutableStateFlow<String>("")
    val thoughtText:StateFlow<String> get() = _thoughtText.asStateFlow()

    private val _refreshEvent = MutableSharedFlow<Unit>()
    val refreshEvent: Flow<Unit> = _refreshEvent
    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing
    //VALUES FOR UPDATE SCREEN -> Manage both screens VM
    //***************************************
    private val _isStartedReading = mutableStateOf(false)
    val isStartedReading:Boolean get() = _isStartedReading.value
    private val _isFinishedReading = mutableStateOf(false)
    val isFinishedReading:Boolean get() = _isFinishedReading.value

    // ***************************************


//     val data: MutableState<DataOrException<List<MBook>,Boolean,Exception>>
//        = mutableStateOf(DataOrException(listOf(),true,Exception("")))

    init{
        getAllBooksFromDatabase()
    }


    internal fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            _listOfBooks.value.loading = true
            _listOfBooks.value = repository.getAllBooksFromDatabase()
            if(!_listOfBooks.value.data.isNullOrEmpty()) {
                _listOfBooks.value.loading = false
            }else {
                updateListOfBooks(emptyList())
                _listOfBooks.value.loading = false
            }
            _isRefreshing.value = false

        }
        Log.d("GET","getAllBooksFromDatabase: ${_listOfBooks.value.data?.toList().toString()}")
    }
    fun updateListOfBooks(books:List<MBook>){
        _listOfBooks.update {currentState->
            currentState.copy(data = books)
        }
    }
    fun updateAddedBooks(){
        _addedBooks.update {
            it.copy(data =_listOfBooks.value.data?.filter {
                it.startedReading == null && it.finishedReading == null
            }, loading = _listOfBooks.value.loading,e = java.lang.Exception(""))
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
    fun setIsRefreshing(value:Boolean){
        _isRefreshing.value = value
    }



}