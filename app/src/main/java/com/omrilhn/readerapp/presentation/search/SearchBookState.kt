package com.omrilhn.readerapp.presentation.search

import com.omrilhn.readerapp.data.model.Item

data class SearchBookState(
    val listOfBooks:List<Item> = emptyList(),
    var isLoading: Boolean = false
)
