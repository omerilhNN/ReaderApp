package com.omrilhn.readerapp.presentation.components.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omrilhn.readerapp.presentation.components.StandardInputField
import com.omrilhn.readerapp.presentation.home.HomeViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(modifier:Modifier = Modifier,
               loading:Boolean = false,
               text:String ,
               homeViewModel: HomeViewModel = hiltViewModel(),
               defaultValue: String = "Great Book!",
               onSearch: (String)-> Unit
               ){

    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(text){text.trim().isNotEmpty() }

    StandardInputField(text = text,
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(3.dp)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        label = "Your thoughts",
        hint = "Enter your thoughts",
        enabled = true,
        onAction = KeyboardActions{
            if(!valid)return@KeyboardActions
            onSearch(text.trim())
            keyboardController?.hide()
        },
        onValueChange = {
            homeViewModel.setThoughtText(it)
        })

}