package com.omrilhn.readerapp.presentation.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.utils.Resource

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>,
                    navController: NavController){
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id



    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        item(){
            Card(modifier = Modifier.padding(2.dp),
                shape = RectangleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.DarkGray)
            ){

                Image(painter = rememberAsyncImagePainter(model = bookData!!.imageLinks.thumbnail),
                    contentDescription ="Book Images",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .padding(2.dp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = bookData?.title.toString(),
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 20)
                Text(text = "Authors: ${bookData?.authors.toString()}")
                Text(text = "Page count: ${bookData?.pageCount.toString()}")
                Text(text = "Categories: ${bookData?.categories.toString()}",
                    style = MaterialTheme.typography.subtitle1)
                Text(text = "Published: ${bookData?.publishedDate.toString()}",
                    style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.padding(5.dp))
            }


        }




    }
}