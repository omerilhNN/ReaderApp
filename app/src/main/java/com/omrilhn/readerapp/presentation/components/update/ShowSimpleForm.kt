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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.omrilhn.readerapp.R
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.presentation.components.RoundedButton
import com.omrilhn.readerapp.presentation.home.HomeViewModel
import com.omrilhn.readerapp.utils.formatDate
import com.omrilhn.readerapp.utils.showToast

@Composable
fun ShowSimpleForm(book:MBook,navController: NavController,homeViewModel: HomeViewModel = hiltViewModel()){
    //You can keep those val's inside VM -> thus you can avoid from recomposition everytime that composable has been called.
    //!! However if complexity of a project not much then you can use these remember val's !!\\
    val notesText = homeViewModel.thoughtText.collectAsState()
    val isStartedReading = homeViewModel.isStartedReading
    val isFinishedReading = homeViewModel.isFinishedReading
    val ratingVal = remember{ mutableIntStateOf(0) }
    val changedNotes = book.notes != notesText.value //If these 2 variables are not same -> true - you can update
    val changedRating = book.rating?.toInt() != ratingVal.intValue
    val isFinishedTimestamp = if (isFinishedReading) Timestamp.now() else book.finishedReading
    val isStartedTimestamp = if(isStartedReading) Timestamp.now() else book.startedReading
    val context = LocalContext.current

    val bookUpdate = changedNotes || changedNotes || isStartedReading || isFinishedReading

    //hashMap K value must be same with the values in Firestore DB -> Book collections element
    val bookToUpdate = hashMapOf(
        "finished_reading_at" to isFinishedTimestamp,
        "started_reading_at" to isStartedTimestamp,
        "rating" to ratingVal.intValue,
        "notes" to notesText.value).toMap() //turn into a HashMap with toMap func

    SimpleForm(text=notesText.value,defaultValue = book.notes.toString().ifEmpty { "No thoughts available." }){ note->
        homeViewModel.setThoughtText(note)
    }
    Row(modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start){
        TextButton(enabled = book.startedReading == null,
            onClick = {
                homeViewModel.setIsReadingStarted()
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
                Text(text = "Started On: ${formatDate(book.startedReading!!)}")
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
                Text(text = "Finished on: ${formatDate(book.finishedReading!!)}")
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
    Row{
        RoundedButton(label = "Update"){
            if(bookUpdate){
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .update(bookToUpdate)
                    .addOnCompleteListener{task->
                        Log.d("FB","ShowSimpleForm: ${task.result.toString()}")
                        showToast(context,"Book Updated Succesfully!")
                        navController.navigate(Screen.HomeScreen.route)
                    }.addOnFailureListener {
                        Log.w("FB","Error updating document",it)
                    }
            }
        }
        Spacer(modifier = Modifier.width(100.dp))
        val openDialog = remember{ mutableStateOf(false)}
        if(openDialog.value){
            ShowAlertDialog(message = stringResource(id = R.string.sure)+ "\n" +
                            stringResource(id = R.string.action),openDialog){
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .delete()
                    .addOnCompleteListener{task->
                        if(task.isSuccessful){
                            openDialog.value = false
                            navController.popBackStack()
                            navController.navigate(Screen.HomeScreen.route)
                        }
                    }
            }
        }
        RoundedButton(label = "Delete"){
            openDialog.value = true

        }
    }

}