package com.omrilhn.readerapp.presentation.components

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.presentation.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HorizontalScrollableItem(listOfBooks:DataOrException<List<MBook>,Boolean,Exception>,
                             viewModel:HomeViewModel = hiltViewModel(),
                             onCardPressed:(String)->Unit){
    val scrollState = rememberScrollState()
//    val listOfBooks = viewModel.listOfBooks.collectAsState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState) ){
        if(listOfBooks.loading == true){
            LinearProgressIndicator()
        }else{
            Log.d("TEST","viewModel data loading == false ")
            if(listOfBooks.data.isNullOrEmpty()){
                Log.d("TEST", listOfBooks.toString())
                Surface(modifier = Modifier.padding(20.dp)) {
                    Text(text = "No books found. Add a book",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize =  14.sp
                        )
                    )
                }
            }else{
                Log.d("TEST","listOfBooks: ${listOfBooks.data.isNullOrEmpty()}")

                for(book in listOfBooks.data!!){
                    ListCard(book){
                        //OnCardPressed -> show details
                        onCardPressed(book.googleBookId.toString())

                    }
                }
            }
        }



    }

}