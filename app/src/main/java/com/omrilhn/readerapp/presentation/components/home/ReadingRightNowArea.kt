package com.omrilhn.readerapp.presentation.components.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.HorizontalScrollableItem

@Composable
fun ReadingRightNowArea(listOfBooks:List<com.omrilhn.readerapp.data.model.MBook>, navController: NavController){
    //Filter books by reading now.
    val readingNowList = listOfBooks.filter { mBook->
        mBook.startedReading != null && mBook.finishedReading ==null
    }
    HorizontalScrollableItem(listOfBooks = readingNowList){
        Log.d("TAG","BookListArea: $it")
        navController.navigate(Screen.UpdateScreen.route+"/$it")
    }
}