package com.omrilhn.readerapp.presentation.components.home

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
fun BookListArea(viewModel:HomeViewModel = hiltViewModel(),
                 navController: NavController){

    val listOfBooks = viewModel.listOfBooks.collectAsState()
    val addedBooks = DataOrException(
        data = listOfBooks.value.data?.filter {
            it.startedReading == null && it.finishedReading == null
        } ?: emptyList(),
        loading = listOfBooks.value.loading,
        Exception("")
    )

    HorizontalScrollableItem(addedBooks){
            navController.navigate(Screen.UpdateScreen.route+"/$it")
    }
}