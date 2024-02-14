package com.omrilhn.readerapp.presentation.components.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.HorizontalScrollableItem

@Composable
fun BookListArea(listOfBooks: List<MBook>,
                 navController: NavController){
    val addedBooks = listOfBooks.filter { mBook->
        mBook.startedReading == null && mBook.finishedReading == null
    }

    HorizontalScrollableItem(addedBooks){
            navController.navigate(Screen.UpdateScreen.route+"/$it")
    }
}