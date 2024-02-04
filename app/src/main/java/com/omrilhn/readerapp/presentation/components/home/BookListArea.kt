package com.omrilhn.readerapp.presentation.components.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.presentation.components.HorizontalScrollableItem

@Composable
fun BookListArea(listOfBooks:List<com.omrilhn.readerapp.data.model.MBook>, navController: NavController){
    HorizontalScrollableItem(listOfBooks){
        //TODO: onCardClicked navigate to details
    }
}