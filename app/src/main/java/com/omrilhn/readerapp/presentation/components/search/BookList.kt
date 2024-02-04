package com.omrilhn.readerapp.presentation.components.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omrilhn.readerapp.data.model.MBook

@Composable
fun BookList(navController: NavController){
    val listOfBooks = listOf(
        com.omrilhn.readerapp.data.model.MBook(
            id = "asd",
            title = "Hello there",
            authors = "All of us ",
            notes = null
        ),
        com.omrilhn.readerapp.data.model.MBook(
            id = "23",
            title = "Hello there",
            authors = "All of us ",
            notes = null
        ),
        com.omrilhn.readerapp.data.model.MBook(
            id = "asdf",
            title = "Hello there",
            authors = "All of us ",
            notes = null
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        items(listOfBooks){book->
            BookRow(book = book, navController = navController)
            
        }

    }
}