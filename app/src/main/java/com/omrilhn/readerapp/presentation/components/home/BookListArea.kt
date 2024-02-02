package com.omrilhn.readerapp.presentation.components.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.omrilhn.readerapp.core.domain.models.MBook
import com.omrilhn.readerapp.presentation.components.HorizontalScrollableItem

@Composable
fun BookListArea(listOfBooks:List<MBook>,navController: NavController){
    HorizontalScrollableItem(listOfBooks){
        //TODO: onCardClicked navigate to details
    }
}