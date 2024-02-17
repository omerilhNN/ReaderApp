package com.omrilhn.readerapp.presentation.components.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omrilhn.readerapp.presentation.search.SearchViewModel

@Composable
fun BookList(navController: NavController,searchViewModel:SearchViewModel){

    val searchBookState = searchViewModel.searchBookState.collectAsState()

    if(searchBookState.value.isLoading) {
        Row(modifier = Modifier.padding(end =2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            LinearProgressIndicator()
            Text(text = "Loading...")
        }
    }else{
        Log.d("BOO","Book list: ${searchBookState.value.listOfBooks}")
        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ){
            items(searchBookState.value.listOfBooks){ book->
                BookRow(book = book, navController = navController)

            }
        }
    }

}