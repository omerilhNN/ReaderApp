package com.omrilhn.readerapp.presentation.components.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.HorizontalScrollableItem
import com.omrilhn.readerapp.presentation.home.HomeViewModel

@Composable
fun ReadingRightNowArea( navController: NavController,
                         viewModel:HomeViewModel = hiltViewModel()){
    //Filter books by reading now.
    val listOfBooks = viewModel.listOfBooks.collectAsState()
    // mBook.startedReading != null && mBook.finishedReading ==null
    val readingNowList = DataOrException(
        data = listOfBooks.value.data?.filter {
            it.startedReading != null && it.finishedReading ==null
        } ?: emptyList(),
        loading = listOfBooks.value.loading,
        Exception(listOfBooks.value.e.toString())


    )
    HorizontalScrollableItem(listOfBooks = readingNowList){
        Log.d("TAG","BookListArea: $it")
        navController.navigate(Screen.UpdateScreen.route+"/$it")
    }
}