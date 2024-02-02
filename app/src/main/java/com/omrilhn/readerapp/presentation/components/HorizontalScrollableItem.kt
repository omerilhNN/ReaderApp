package com.omrilhn.readerapp.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.omrilhn.readerapp.core.domain.models.MBook

@Composable
fun HorizontalScrollableItem(listOfBooks:List<MBook>,onCardPressed:(String)->Unit){
    val scrollState = rememberScrollState()

    Row(modifier = Modifier.fillMaxWidth().heightIn(280.dp).horizontalScroll(scrollState) ){

        for(book in listOfBooks){
            ListCard(book){
                //OnCardPressed -> show details
                onCardPressed(it)

            }
        }

    }

}