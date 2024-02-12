package com.omrilhn.readerapp.presentation.components.update

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.presentation.components.RoundedButton
import com.omrilhn.readerapp.presentation.home.HomeViewModel

@Composable
fun ShowSimpleForm(book:MBook,navController: NavController,homeViewModel: HomeViewModel = hiltViewModel()){
    //You can keep those val's inside VM -> thus you can avoid from recomposition everytime that composable has been called.
    //!! However if complexity of a project not much then you can use these remember val's !!\\
    val notesText = remember{ mutableStateOf("") }
    val isStartedReading = homeViewModel.isStartedReading
    val isFinishedReading = homeViewModel.isFinishedReading
    val ratingVal = remember{ mutableIntStateOf(0) }

    SimpleForm(defaultValue = if(book.notes.toString().isNotEmpty()) book.notes.toString()
    else "No thoughts available."){note->
        notesText.value = note
    }
    Row(modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start){
        TextButton(enabled = book.startedReading == null,
            onClick = {

            }) {
            if (book.startedReading == null) {
                if(!isStartedReading){
                    Text(text ="Start Reading")
                }else{
                    Text(text = "Started Reading!",
                    modifier = Modifier.alpha(0.6f),
                    color = Color.Red.copy(alpha = 0.5f))
                }
            }else{
                //TODO:FORMAT DATE
                Text(text = "started On: ${book.startedReading}")
            }
        }
        Spacer(modifier = Modifier.width(4.dp))

        TextButton(onClick={
            homeViewModel.setIsFinishedReading()
        },
           enabled = book.finishedReading == null){
            if(book.finishedReading == null){
                if(!isFinishedReading){
                    Text(text = "Mark as Read")
                }else{
                    Text(text = "Finished Reading!")
                }}
            else{
                Text(text = "Finished on: ${book.finishedReading}") //TODO: Format
            }
        }
    }
    Text(text = "Rating",modifier = Modifier.padding(bottom = 3.dp))
    book.rating?.toInt().let{
        RatingBar(rating = it!!){rating->
            ratingVal.intValue = rating
            Log.d("TAG","ShowSimpleForm: ${ratingVal.intValue}")
        }
    }
    Spacer(modifier = Modifier.padding(bottom = 15.dp))
    Row(horizontalArrangement = Arrangement.SpaceBetween){
        RoundedButton(
            label = "Update"
        )
        RoundedButton(label = "Delete")
    }

}