package com.omrilhn.readerapp.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FABContent(onTap: () -> Unit) {
    //FAB composable for adding new book
    FloatingActionButton(onClick = { onTap()}
        ,shape = RoundedCornerShape(50.dp),
        containerColor = Color(0xFF92CBDF)) {

        Icon(imageVector = Icons.Default.Add,contentDescription = null,
            tint = Color.White)

    }

}