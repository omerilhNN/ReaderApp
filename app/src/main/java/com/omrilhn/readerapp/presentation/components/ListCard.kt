package com.omrilhn.readerapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.omrilhn.readerapp.core.domain.models.MBook
import com.omrilhn.readerapp.presentation.components.home.BookRating

@Preview
@Composable
fun ListCard(book:MBook = MBook("1","asd","1asd","123"),
             onPressDetails: (String) -> Unit = {}){
    val context = LocalContext.current //Know things about whats going on, on screen
    val resources = context.resources //get Resources on current context

    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    val spacing = 10.dp
    Card(
        shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colors.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) } //use invoke in order to make code READABLE
    ){
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2 )),
            horizontalAlignment = Alignment.Start){
            Row(horizontalArrangement = Arrangement.Center){
                Image(painter = rememberAsyncImagePainter("")
                    ,contentDescription = "BookLogo"
                    ,modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp))

                Spacer(modifier = Modifier.weight(0.7f,fill = false))

                Column(modifier = Modifier.padding(top = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Icon(imageVector = Icons.Rounded.FavoriteBorder, contentDescription ="Fav icon" ,
                        modifier = Modifier.padding(bottom = 1.dp)
                        )
                    BookRating(3.5)
                }
            }
            Text(text = "Book title", modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2 ,
                overflow = TextOverflow.Ellipsis)

            Text(text = "Authors. All..",modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.caption)

            Row(horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom){
                RoundedButton("Reading",radius = 70)
            }
        }


    }

}