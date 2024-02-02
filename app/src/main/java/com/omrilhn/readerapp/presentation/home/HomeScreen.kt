package com.omrilhn.readerapp.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.FABContent
import com.omrilhn.readerapp.presentation.components.ReaderAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
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
            //TODO: Home content
        }

    }
}


