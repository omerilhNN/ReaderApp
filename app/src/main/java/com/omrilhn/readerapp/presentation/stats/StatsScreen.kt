package com.omrilhn.readerapp.presentation.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.ReaderAppBar
import com.omrilhn.readerapp.presentation.home.HomeViewModel
import com.omrilhn.readerapp.utils.formatDate
import java.util.Locale

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
            showProfile = false){
                navController.popBackStack()
        }
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
            Column {
                Row{
                  Box(modifier = Modifier
                      .size(45.dp)
                      .padding(2.dp)){
                      Icon(imageVector = Icons.Sharp.Person, contentDescription = "Person icon")
                  }
                    //Display name split process
                    Text(text = "Hi, ${ currentUser?.email.toString().split("@")[0]
                        .uppercase(Locale.getDefault())}")
                }
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)){

                    //books that have been finished
                    val readBookList:List<MBook> = if(!listOfBooks.value.data.isNullOrEmpty()){
                        books.filter { mBook->
                            (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null) }
                    }else emptyList()

                    //currently reading books
                    val readingBooks:List<MBook> = books.filter { mBook->
                        (mBook.startedReading != null) && (mBook.finishedReading == null)
                    }
                    Column(modifier = Modifier.padding(start = 25.dp,top = 4.dp,bottom = 4.dp),
                        horizontalAlignment = Alignment.Start){
                        Text(text = "Your Stats",style = androidx.compose.material.MaterialTheme.typography.h5)
                        Divider()
                        Text(text = "You're reading: ${readingBooks.size} books.")
                        Text(text = "You've read: ${readBookList.size} books.")
                    }
                }
                if(listOfBooks.value.loading == true){
                    LinearProgressIndicator()
                }
                else {
                    Divider()
                    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing =viewModel.isRefreshing.value ),
                        onRefresh = {
                            viewModel.setIsRefreshing(true)
                            viewModel.getAllBooksFromDatabase()
                        }) {
                        LazyColumn(modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp)
                        ){
                            //Filter books by read ones
                            val readBooks:List<MBook> = if(!listOfBooks.value.data.isNullOrEmpty()){
                                listOfBooks.value.data!!.filter{ mBook->
                                    (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                                }
                            }else{ emptyList() }
                            items(items = readBooks ){book->
                                BookRowStats(book = book)

                            }

                        }
                    }

                }
            }
        }

    }

}
@Composable
fun BookRowStats(book: MBook){
    Card(modifier = Modifier
        .clickable {
//            navController.navigate(Screen.BookDetailsScreen.route + "/${book.id}")
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
            val imageUrl:String = if(book.photoUrl.toString().isEmpty())
                "http://books.google.com/books/content?id=kyylDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            else{
                book.photoUrl.toString()
            }

            Image(painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp))

            //Column for book infos
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis )
                    if(book.rating!! >= 4){
                        Icon(imageVector = Icons.Default.ThumbUp,
                            contentDescription ="Thumbs Up",
                            tint = Color.Green.copy(alpha = 0.5f))
                    }else{
                        Box{}
                    }
                }


                Text(text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Normal,
                    style = androidx.compose.material.MaterialTheme.typography.caption.copy(
                        //Author will be highlighted BOLD
                        fontWeight = FontWeight.Bold
                    ))

                Text(text = "Started: ${formatDate(book.startedReading!!)}",
                    overflow = TextOverflow.Clip,
                    softWrap = true,
                    fontStyle = FontStyle.Italic,
                    style = androidx.compose.material.MaterialTheme.typography.caption)

                Text(text = "Finished: ${formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Clip,
                    softWrap = true,
                    fontStyle = FontStyle.Italic,
                    style = androidx.compose.material.MaterialTheme.typography.caption)



                //TODO: Specify those fields later.
            }
        }

    }

}