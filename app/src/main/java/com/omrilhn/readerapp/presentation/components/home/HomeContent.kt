package com.omrilhn.readerapp.presentation.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen

@Composable
fun HomeContent(navController: NavController){
    val listOfBooks = listOf(
        com.omrilhn.readerapp.data.model.MBook(
            id = "asd",
            title = "Hello there",
            authors = "All of us ",
            notes = null
        ),
        com.omrilhn.readerapp.data.model.MBook(
            id = "23",
            title = "Hello there",
            authors = "All of us ",
            notes = null
        ),
        com.omrilhn.readerapp.data.model.MBook(
            id = "asdf",
            title = "Hello there",
            authors = "All of us ",
            notes = null
        )
    )

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
            Column(horizontalAlignment = Alignment.End){
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
        ReadingRightNowArea(books = listOf(), navController = navController)
        
        TitleSection(label = "Reading List")

        BookListArea(listOfBooks = listOfBooks, navController =navController)

    }

}
