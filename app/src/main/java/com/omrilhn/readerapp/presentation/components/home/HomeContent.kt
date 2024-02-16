package com.omrilhn.readerapp.presentation.components.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.FirebaseAuth
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.home.HomeViewModel

@Composable
fun HomeContent(navController: NavController, viewModel: HomeViewModel = hiltViewModel()){
    val currentUser = FirebaseAuth.getInstance().currentUser
    val listOfBooks = viewModel.listOfBooks.collectAsState()
    val isRefreshing = viewModel.isRefreshing


    // if data inside viewModel's books ->  initialize "listOfBooks" with it
    if(!listOfBooks.value.data.isNullOrEmpty()){
         val _listOfBooks = listOfBooks.value.data!!.toList().filter {mBook->
            mBook.userId == currentUser?.uid.toString()
        }
        viewModel.updateListOfBooks(_listOfBooks)
    }else {Log.d("BOOK HomeContent","List of books empty!!!")}

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if(!email.isNullOrEmpty())
        //Get characters before '@'
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    else "N/A"
    Column(modifier = Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top){

        Row(modifier = Modifier.align(alignment = Alignment.Start)){
            TitleSection(label = "Your reading\n" + " activity right now..")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f) )

            Column{
                Icon(imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable { // when Profile account clicked which is on the TopBar
                            navController.navigate(Screen.StatsScreen.route)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colorScheme.secondaryContainer)
                //set text if currentUserName is not NULL
                Text(text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                Divider()

            }

        }
        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value ),
            onRefresh = {
                viewModel.setIsRefreshing(true)
                viewModel.getAllBooksFromDatabase()
            }) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(start = 2.dp, end = 2.dp)){

                item{
                    ReadingRightNowArea( navController = navController)

                    TitleSection(label = "Reading List")

                    //if data is not null -> execute that block of code
                    BookListArea( navController =navController)
                }

            }

        }


    }

}
