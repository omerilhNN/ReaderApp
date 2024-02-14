package com.omrilhn.readerapp.presentation.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.presentation.components.ReaderAppBar
import com.omrilhn.readerapp.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()){
    val currentUser = FirebaseAuth.getInstance().currentUser
    val listOfBooks = viewModel.listOfBooks.collectAsState()
    var books : List<MBook>
    Scaffold(topBar = {
        ReaderAppBar(title = "Book Stats",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false)
    }) {paddingValues ->

        Surface(modifier = Modifier.padding(paddingValues)) {
                //Show books that user have been read
                books = if(!listOfBooks.value.data.isNullOrEmpty()){
                    listOfBooks.value.data!!.filter { mBook->
                        (mBook.userId == currentUser?.uid)
                    }
                }else {
                    emptyList()
                }
            Column(){}
        }

    }

}