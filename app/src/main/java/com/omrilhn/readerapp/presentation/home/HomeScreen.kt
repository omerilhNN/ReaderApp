package com.omrilhn.readerapp.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.FABContent
import com.omrilhn.readerapp.presentation.components.ReaderAppBar
import com.omrilhn.readerapp.presentation.components.home.HomeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,homeViewModel: HomeViewModel = hiltViewModel()){
    //Using Scaffold in order to show TopAppBar
    Scaffold(topBar = {
                      ReaderAppBar(title = "Your Reader", navController = navController )
    },
        floatingActionButton = {
            FABContent{
                navController.navigate(Screen.SearchScreen.route)

            }
        }){ paddingValues->
        //Content
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues))   {
                HomeContent(navController = navController,viewModel = homeViewModel)
        }

    }
}


