package com.omrilhn.readerapp.presentation.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.RoundedButton
import com.omrilhn.readerapp.presentation.home.HomeViewModel
import com.omrilhn.readerapp.utils.Resource

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>,
                    navController: NavController,
                    bookDetailsViewModel: BookDetailsViewModel = hiltViewModel(),
                    homeViewModel: HomeViewModel = hiltViewModel()){
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    val cleanDescription = if(!bookData!!.description.isNullOrEmpty()) HtmlCompat.fromHtml(
        bookData.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY).toString() else "No description available"

    /// Null check on texts that comes from Google Api ********
    val authorsText = if (!bookData.authors.isNullOrEmpty()) bookData.authors.joinToString() else "No author information available"
    val pageCountText = if (!bookData.pageCount.toString().isNullOrEmpty()) bookData.pageCount else "No page count available."
    val categoriesText = if (!bookData.categories.isNullOrEmpty()) bookData.categories.joinToString() else "No categories available."
    val descriptionText = if(!bookData.description.isNullOrEmpty()) bookData.description.toString() else "No description available."
    val photoUrl = if (!bookData.imageLinks.thumbnail.isNullOrEmpty()) bookData.imageLinks.thumbnail else "http://books.google.com/books/content?id=kyylDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
    val publishedDateText = if (bookData.publishedDate.isNullOrEmpty()) "Published date information not available" else bookData.publishedDate
    val titleText = if (!bookData.title.isNullOrEmpty()) bookData.title.toString() else "No title available"
    /// ************
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        item(){
            Card(modifier = Modifier.padding(2.dp),
                shape = RectangleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.DarkGray)
            ){

                Image(painter = rememberAsyncImagePainter(model = bookData.imageLinks.thumbnail),
                    contentDescription ="Book Images",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .padding(2.dp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = titleText,
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 20)
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Authors: ")
                    }
                    append(authorsText.toString())
                })
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Page count: ")
                    }
                    append(pageCountText.toString())
                })
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Categories: ")
                    }
                    append(categoriesText.toString())
                },
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)
                Text( text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Published: ")
                    }
                    append(publishedDateText.toString())
                },
                    style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.padding(5.dp))
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                    thickness = 2.dp,
                    color = Color.Black)
                Text(text =cleanDescription,lineHeight = 15.sp, modifier = Modifier.padding(4.dp))

                Row(modifier = Modifier.padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    RoundedButton(label = "Save"){
                        //OnClick-> save this book to the Firebase DB
                        val book = MBook(
                            title = titleText,
                            authors = authorsText,
                            description = descriptionText,
                            categories = categoriesText,
                            notes = "",
                            photoUrl = photoUrl,
                            publishedDate = publishedDateText,
                            pageCount = pageCountText.toString(),
                            rating = 0.0,
                            googleBookId = googleBookId,
                            userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

                        )
                        bookDetailsViewModel.saveToFirestore(book){
                            //onNavigate lambda fun parameter assigned
                            homeViewModel.getAllBooksFromDatabase()
                            navController.popBackStack()
                        }


                    }
                    Spacer(modifier = Modifier.width(25.dp))
                    RoundedButton(label = "Back"){
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}