package com.omrilhn.readerapp.presentation.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.presentation.components.CardListItem

@Composable
fun ShowBookUpdate(bookInfo: DataOrException<List<MBook>,Boolean,Exception>,
                   bookItemId:String){
    Row(){
        Spacer(modifier = Modifier.width(40.dp))
        if(bookInfo.data!= null){
            Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center) {
                CardListItem(book = bookInfo.data!!.first{mBook->
                    // Will return first element of the list -> if that item satisfying the condition down below
                    mBook.googleBookId == bookItemId
                }) {

                }

            }
        }

    }

}