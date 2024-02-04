package com.omrilhn.readerapp.presentation.components.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.omrilhn.readerapp.presentation.components.StandardInputField
import com.omrilhn.readerapp.presentation.search.SearchViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier:Modifier = Modifier,
    loading:Boolean = false,
    hint:String = "Search",
    searchViewModel: SearchViewModel = hiltViewModel(),
    onSearch: (String) -> Unit = {},
){
    val searchText = searchViewModel.searchQueryState.collectAsState()
    Column(){
        val keyboardController = LocalSoftwareKeyboardController.current

        val valid = remember(searchText.value.text){
            searchText.value.text.isNotEmpty()
        }
        StandardInputField(text = searchText.value.text,
            onAction = KeyboardActions {
                if(!valid) return@KeyboardActions
                onSearch(searchText.value.text.trim())
                searchText.value.copy( text = "")
                keyboardController?.hide()
        },
            hint = "Enter a book name to Search",
            label = "Search",
            onValueChange = {})


    }

}