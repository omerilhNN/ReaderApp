package com.omrilhn.readerapp.presentation.components.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.omrilhn.readerapp.data.model.MBook

@Composable
fun BookRow(book: com.omrilhn.readerapp.data.model.MBook, navController: NavController){
    Card(modifier = Modifier
        .clickable { }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(7.dp)){
        //Row for keep Image and Column together
        Row(modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.Top){
                val imageUrl = "http://books.google.com/books/content?id=kyylDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                Image(painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = "book image",
                    modifier = Modifier.width(80.dp)
                        .fillMaxHeight().padding(end = 4.dp))

                //Column for book infos
                Column {
                    Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis )
                    Text(text = "Author: ${book.authors}", overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.caption)
                    //TODO: Specify those fields later.
                }
            }

    }

}