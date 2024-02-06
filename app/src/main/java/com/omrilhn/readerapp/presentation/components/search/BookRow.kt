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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen

@Composable
fun BookRow(book: Item, navController: NavController){
    Card(modifier = Modifier
        .clickable {
            navController.navigate(Screen.BookDetailsScreen.route +  "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(7.dp)){
        //Row for keep Image and Column together
        Row(modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.Top){

            //If Book doesn't have image set its thumbnail to default else -> set its thumbnail
            val imageUrl:String = if(book.volumeInfo.imageLinks.smallThumbnail.isEmpty() == true)
                "http://books.google.com/books/content?id=kyylDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            else{
                book.volumeInfo.imageLinks.smallThumbnail
            }

            Image(painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = "book image",
                    modifier = Modifier.width(80.dp)
                        .fillMaxHeight().padding(end = 4.dp))

                //Column for book infos
                Column {
                    Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis )

                    Text(text = "Author: ${book.volumeInfo.authors}", overflow = TextOverflow.Clip,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.caption.copy(
                            //Author will be highlighted BOLD
                            fontWeight = FontWeight.Bold
                        ))

                    Text(text = "Date: ${book.volumeInfo.publishedDate}", overflow = TextOverflow.Clip,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.caption)

                    Text(text = "${book.volumeInfo.categories}", overflow = TextOverflow.Clip,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.caption)

                    //TODO: Specify those fields later.
                }
            }

    }

}