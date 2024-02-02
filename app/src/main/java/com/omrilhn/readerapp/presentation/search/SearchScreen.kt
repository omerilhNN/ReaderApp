package com.omrilhn.readerapp.presentation.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.ReaderAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController){
    Scaffold(topBar = {
        ReaderAppBar(title = "Search books",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false){
            navController.navigate(Screen.HomeScreen.route)
        }
    }){padding->

    }

}