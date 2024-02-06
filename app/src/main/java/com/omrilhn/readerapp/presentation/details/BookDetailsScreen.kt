package com.omrilhn.readerapp.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.ReaderAppBar
import com.omrilhn.readerapp.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(navController: NavController,
                      bookId:String,
                      bookDetailsViewModel: BookDetailsViewModel = hiltViewModel()){
    Scaffold(topBar = {
        ReaderAppBar(title = "Book Details",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false){
                navController.navigate(Screen.SearchScreen.route)
        }
    } ){paddingValues ->

        Surface(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()){
            Column(modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally){
                //Producing state to be able to execute suspend fun inside a COMPOSABLE
                val bookInfo = produceState<Resource<Item>>(initialValue =Resource.Loading()){
                    value = bookDetailsViewModel.getBookInfo(bookId)
                }.value

                if(bookInfo.data == null){
                    Row(){
                        LinearProgressIndicator()
                        Text(text = "Loading...")
                    }
                }else{
                    ShowBookDetails(bookInfo = bookInfo, navController = navController)
                }
            }

        }

    }

}