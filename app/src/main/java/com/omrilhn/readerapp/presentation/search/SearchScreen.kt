package com.omrilhn.readerapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.ReaderAppBar
import com.omrilhn.readerapp.presentation.components.SearchForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController){
    Scaffold(topBar = {
        ReaderAppBar(title = "Search books",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false){
            navController.popBackStack() // 27 & 28 lines do the same job
//            navController.navigate(Screen.HomeScreen.route)
        }
    }){padding->
        Surface(modifier = Modifier.padding(padding)){
            Column {
                SearchForm(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }

}