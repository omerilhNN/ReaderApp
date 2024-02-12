package com.omrilhn.readerapp.presentation.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.presentation.home.HomeViewModel

@Composable
fun HorizontalScrollableItem(listOfBooks:List<com.omrilhn.readerapp.data.model.MBook>,
                             viewModel:HomeViewModel = hiltViewModel()  ,
                             onCardPressed:(String)->Unit){
    val scrollState = rememberScrollState()


    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState) ){
        if(viewModel.data.value.loading == true){
            LinearProgressIndicator()
        }else{
            if(listOfBooks.isEmpty()){
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
                for(book in listOfBooks){
                    ListCard(book){
                        //OnCardPressed -> show details
                        onCardPressed(book.googleBookId.toString())

                    }
                }
            }
        }



    }

}