package com.omrilhn.readerapp.presentation.components.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowAlertDialog(
    message:String,
    openDialog:MutableState<Boolean>,
    onYesPressed: () -> Unit
){
    if(openDialog.value){
        AlertDialog(onDismissRequest = { openDialog.value = false },
            modifier = Modifier.padding(5.dp),
            title = {Text(text = "Delete Book")},
            text = {Text(text = message)},
            buttons = {
                Row(modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    TextButton(onClick = {onYesPressed.invoke()}) {
                        Text(text = "Yes")
                    }
                    TextButton(onClick = {openDialog.value = false }) {
                        Text(text = "No")
                    }

                }
            })
    }

}