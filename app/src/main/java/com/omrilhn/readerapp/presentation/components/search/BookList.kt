package com.omrilhn.readerapp.presentation.components.search

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omrilhn.readerapp.presentation.search.SearchViewModel

@Composable
fun BookList(navController: NavController,searchViewModel:SearchViewModel){

    val searchBookState = searchViewModel.searchBookState.collectAsState()

    if(searchBookState.value.isLoading) {
        Log.d("BOO","BookList: loading...")
        CircularProgressIndicator()
    }else{
        Log.d("BOO","Book list: ${searchBookState.value.listOfBooks}")
    }





    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
       items(searchBookState.value.listOfBooks){ book->

       }
    }
}